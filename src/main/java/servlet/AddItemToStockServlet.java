package servlet;

import repository.StockRepository;
import service.StockService;
import model.StockBatch;
import util.DatabaseConnectionManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;

@WebServlet(value = "/addItemToStock", asyncSupported = true)
public class AddItemToStockServlet extends HttpServlet {

    private StockService stockService;
    private final Object lock = new Object(); // Lock object for synchronization

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DatabaseConnectionManager.getInstance().getConnection();
            StockRepository stockRepository = StockRepository.getInstance(connection);
            stockService = StockService.getInstance(stockRepository);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward to the JSP page to display the form
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/addItemToStock.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get form parameters from the request
        String itemCode = request.getParameter("itemCode");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        Date purchaseDate = Date.valueOf(request.getParameter("purchaseDate"));
        Date expiryDate = Date.valueOf(request.getParameter("expiryDate"));

        // Create a StockBatch using the builder pattern
        StockBatch.StockBatchBuilder builder = new StockBatch.StockBatchBuilder();
        builder.setItemCode(itemCode)
                .setQuantity(quantity)
                .setPurchaseDate(purchaseDate)
                .setExpiryDate(expiryDate);

        StockBatch stockBatch = builder.build();

        // Synchronize the critical section to ensure thread safety
        synchronized (lock) {
            // Call the service to add the stock batch
            stockService.addStockBatch(stockBatch);
        }

        // Set a success message to be displayed in the JSP
        request.setAttribute("message", "Item added to stock successfully.");

        // Forward back to the same JSP to display the success message
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/addItemToStock.jsp");
        dispatcher.forward(request, response);
    }
}
