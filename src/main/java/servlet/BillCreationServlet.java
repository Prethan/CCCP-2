package servlet;

import model.Bill;
import model.Item;
import repository.*;
import service.BillingService;
import service.ShelfService;
import service.StockService;
import util.DatabaseConnectionManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = "/createBill",asyncSupported = true)
public class BillCreationServlet extends HttpServlet {

    private BillingService billingService;
    private ItemRepository itemRepository;
    private final Object lock = new Object();  // Lock object for synchronization

    @Override
    public void init() throws ServletException {
        // Initialize your services and repositories here
        try {
            Connection connection = DatabaseConnectionManager.getInstance().getConnection();
            BillRepository billRepository = BillRepository.getInstance(connection);
            itemRepository = ItemRepository.getInstance(connection);
            StockRepository stockRepository = StockRepository.getInstance(connection);
            SaleRepository saleRepository = SaleRepository.getInstance(connection);
            StockService stockService = StockService.getInstance(stockRepository);
            ShelfRepository shelfRepository = ShelfRepository.getInstance(connection);
            ShelfService shelfService = ShelfService.getInstance(stockRepository, stockService, shelfRepository);
            billingService = BillingService.getInstance(billRepository, itemRepository, stockService, saleRepository, shelfService);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward to JSP form for bill creation
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/billCreation.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Bill.BillItem> billItems = new ArrayList<>();

        String[] itemCodes = request.getParameterValues("itemCode");
        String[] quantities = request.getParameterValues("quantity");

        // Synchronize the critical section where bill items and bill are created
        synchronized (lock) {
            if (itemCodes != null && quantities != null) {
                for (int i = 0; i < itemCodes.length; i++) {
                    String itemCode = itemCodes[i];
                    int quantity = Integer.parseInt(quantities[i]);

                    // Fetch item price from the database using ItemRepository
                    Item item = itemRepository.getItemByCode(itemCode);
                    double price = item.getPrice();

                    // Create BillItem using BillingService
                    Bill.BillItem billItem = billingService.createBillItem(itemCode, quantity, price);
                    billItems.add(billItem);
                }
            }

            // Get cash tendered
            double cashTendered = Double.parseDouble(request.getParameter("cashTendered"));

            // Create Bill using BillingService
            Bill bill = billingService.createBill(billItems, cashTendered);

            // Set the created bill as a request attribute and forward to a JSP to display the bill
            request.setAttribute("bill", bill);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/displayBill.jsp");
        dispatcher.forward(request, response);
    }
}
