package servlet;

import report.BillReport;
import report.DailySalesReport;
import repository.*;
import service.ReportService;
import util.DatabaseConnectionManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Map;

@WebServlet("/dailySalesReport")
public class DailySalesReportServlet extends HttpServlet {

    private SaleRepository saleRepository;

    private ReportService reportService;

    @Override
    public void init() {
        try {
            Connection connection = DatabaseConnectionManager.getInstance().getConnection();
            BillRepository billRepository = BillRepository.getInstance(connection);
            ItemRepository itemRepository = ItemRepository.getInstance(connection);
            StockRepository stockRepository = StockRepository.getInstance(connection);
            saleRepository = SaleRepository.getInstance(connection);
            ShelfRepository shelfRepository = ShelfRepository.getInstance(connection);
            reportService = ReportService.getInstance(billRepository, saleRepository, stockRepository, itemRepository, shelfRepository);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String salesDateParam = request.getParameter("salesDate"); // Get date from request

        if (salesDateParam != null) {
            try {
                // Convert the string date to SQL Date
                Date salesDate = Date.valueOf(salesDateParam);

                // Generate the sales report based on the user-selected date
                DailySalesReport dailySalesReport = reportService.generateDailySalesReport(salesDate);

                // Set the report as a request attribute and forward it to the JSP
                request.setAttribute("dailySalesReport", dailySalesReport);
                request.getRequestDispatcher("/WEB-INF/views/dailySalesReport.jsp").forward(request, response);
            } catch (IllegalArgumentException e) {
                // Handle invalid date format
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid date format");
            }
        } else {
            // If no date was provided, redirect back to the date selection page
            response.sendRedirect("selectDate.jsp");
        }
    }
}
