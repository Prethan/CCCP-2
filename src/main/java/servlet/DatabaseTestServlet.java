package servlet;

import util.DatabaseConnectionManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/testdb")
public class DatabaseTestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");

        try {
            // Get a connection from the DatabaseConnectionManager
            Connection connection = DatabaseConnectionManager.getInstance().getConnection();

            if (connection != null) {
                response.getWriter().println("Database connection successful!");
            } else {
                response.getWriter().println("Failed to connect to the database.");
            }
        } catch (SQLException e) {
            response.getWriter().println("Error connecting to the database: " + e.getMessage());
        }
    }
}
