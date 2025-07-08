package servlet;

import report.BillReport;
import report.StockReport;
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
import java.util.List;

@WebServlet("/stockReport")
public class StockReportServlet extends HttpServlet {

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
        List<StockReport> stockReports = reportService.generateStockReport();

        request.setAttribute("stockReports", stockReports);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/stockReport.jsp");
        dispatcher.forward(request, response);
    }
}
