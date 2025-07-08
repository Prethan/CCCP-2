package repository;

import model.Sale;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * SOLID Principles used in SaleRepository class
 *
 * SRP: The SaleRepository class is responsible for database operations related to Sale entities.
 * OCP: The SaleRepository class can be extended with additional query methods without modifying existing methods.
 * LSP: The class follows LSP as it can be replaced by any other implementation adhering to the same contract.
 * ISP: Not directly applicable as it's a concrete class focusing on a single responsibility.
 * DIP: The class depends on the abstract concept of a database connection rather than a specific implementation.
 */

// Singleton Pattern
public class SaleRepository {
    private static SaleRepository instance; // Singleton instance
    private Connection connection;

    // Singleton Pattern: Private constructor to prevent instantiation from outside
    private SaleRepository(Connection connection) {
        this.connection = connection;
    }

    // Singleton Pattern: Static method to get the singleton instance
    public static SaleRepository getInstance(Connection connection) {
        if (instance == null) {
            instance = new SaleRepository(connection);
        }
        return instance;
    }

    /**
     * Adds a new sale to the database.
     *
     * @param sale The sale to be added.
     */
    public void addSale(Sale sale) {
        String query = "INSERT INTO Sales (sales_date, item_code, quantity_sold, total_revenue) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setDate(1, new Date(sale.getSalesDate().getTime()));
            pstmt.setString(2, sale.getItemCode());
            pstmt.setInt(3, sale.getQuantitySold());
            pstmt.setDouble(4, sale.getTotalRevenue());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves sales from the database based on a specific date.
     *
     * @param date The date to filter sales.
     * @return A list of sales on the specified date.
     */
    public List<Sale> findSalesByDate(Date date) {
        List<Sale> sales = new ArrayList<>();
        String query = "SELECT * FROM Sales WHERE sales_date = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setDate(1, date);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Sale sale = createSaleFromResultSet(rs);
                    sales.add(sale);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sales;
    }

    // Factory Method Pattern: Creates Sale objects from ResultSet
    private Sale createSaleFromResultSet(ResultSet rs) throws SQLException {
        return new Sale.SaleBuilder()
                .setSalesId(rs.getInt("sales_id"))
                .setSalesDate(rs.getDate("sales_date"))
                .setItemCode(rs.getString("item_code"))
                .setQuantitySold(rs.getInt("quantity_sold"))
                .setTotalRevenue(rs.getDouble("total_revenue"))
                .build();
    }
}
