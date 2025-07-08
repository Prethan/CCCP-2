//package servlet;
//
//import model.Item;
//import repository.*;
//import service.ShelfService;
//import service.StockService;
//import util.DatabaseConnectionManager;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.List;
//
//@WebServlet("/reshelfItems")
//public class ReshelfItemsServlet extends HttpServlet {
//
//    private ShelfService shelfService;
//
//    @Override
//    public void init() throws ServletException {
//        try {
//            Connection connection = DatabaseConnectionManager.getInstance().getConnection();
//            StockRepository stockRepository = StockRepository.getInstance(connection);
//            ShelfRepository shelfRepository = ShelfRepository.getInstance(connection);
//            StockService stockService = StockService.getInstance(stockRepository);
//            shelfService = ShelfService.getInstance(stockRepository, stockService, shelfRepository);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // Call the reshelfItems() function and get the restocked items
//        List<Item> restockedItems = shelfService.reshelfItems();
//
//        // Set the list of restocked items and a success message for the JSP
//        request.setAttribute("message", "Items below the threshold have been restocked successfully!");
//        request.setAttribute("restockedItems", restockedItems); // Pass the items list to JSP
//
//        // Forward the request to the JSP file to display the message and the items
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/reshelfItems.jsp");
//        dispatcher.forward(request, response);
//    }
//}
package servlet;

import model.Item;
import repository.*;
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
import java.util.List;

@WebServlet(value = "/reshelfItems", asyncSupported = true)
public class ReshelfItemsServlet extends HttpServlet {

    private ShelfService shelfService;
    private final Object lock = new Object(); // Lock object for synchronization

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DatabaseConnectionManager.getInstance().getConnection();
            StockRepository stockRepository = StockRepository.getInstance(connection);
            ShelfRepository shelfRepository = ShelfRepository.getInstance(connection);
            StockService stockService = StockService.getInstance(stockRepository);
            shelfService = ShelfService.getInstance(stockRepository, stockService, shelfRepository);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Item> restockedItems;

        // Synchronize the reshelving operation to ensure thread safety
        synchronized (lock) {
            // Call the reshelfItems() function and get the restocked items
            restockedItems = shelfService.reshelfItems();
        }

        // Set the list of restocked items and a success message for the JSP
        request.setAttribute("message", "Items below the threshold have been restocked successfully!");
        request.setAttribute("restockedItems", restockedItems); // Pass the items list to JSP

        // Forward the request to the JSP file to display the message and the items
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/reshelfItems.jsp");
        dispatcher.forward(request, response);
    }
}
