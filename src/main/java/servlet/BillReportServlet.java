package servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import report.BillReport;
import repository.*;
import service.ReportService;
import util.DatabaseConnectionManager;

@WebServlet("/billReport")
public class BillReportServlet extends HttpServlet {

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
        BillReport billReport = reportService.generateBillReport();

        request.setAttribute("billReport", billReport);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/billReport.jsp");
        dispatcher.forward(request, response);
    }
}
