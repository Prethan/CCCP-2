package servlet;

import report.BillReport;
import report.ReorderReport;
import repository.*;
import service.ReportService;
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

@WebServlet("/reorderReport")
public class ReorderReportServlet extends HttpServlet {

    private ReportService reportService;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Connection connection = DatabaseConnectionManager.getInstance().getConnection();
            BillRepository billRepository = BillRepository.getInstance(connection);
            ItemRepository itemRepository = ItemRepository.getInstance(connection);
            StockRepository stockRepository = StockRepository.getInstance(connection);
            SaleRepository saleRepository = SaleRepository.getInstance(connection);
            ShelfRepository shelfRepository = ShelfRepository.getInstance(connection);
            reportService = ReportService.getInstance(billRepository, saleRepository, stockRepository, itemRepository, shelfRepository);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ReorderReport itemsBelowReorderLevel = reportService.generateReorderReport();

        request.setAttribute("itemsBelowReorderLevel", itemsBelowReorderLevel);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/reorderReport.jsp");
        dispatcher.forward(request, response);
    }
}
