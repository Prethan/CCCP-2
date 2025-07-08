package repository;

import model.Item;
import model.StockBatch;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * SOLID Principles used in StockRepository class
 *
 * SRP: The StockRepository class is responsible for database operations related to StockBatch entities.
 * OCP: The StockRepository class can be extended with additional query methods without modifying existing methods.
 * LSP: The class follows LSP as it can be replaced by any other implementation adhering to the same contract.
 * ISP: Not directly applicable as it's a concrete class focusing on a single responsibility.
 * DIP: The class depends on the abstract concept of a database connection rather than a specific implementation.
 */

// Singleton Pattern
public class StockRepository {
    private static StockRepository instance; // Singleton instance
    private Connection connection;

    // Singleton Pattern: Private constructor to prevent instantiation from outside
    private StockRepository(Connection connection) {
        this.connection = connection;
    }

    // Singleton Pattern: Static method to get the singleton instance
    public static StockRepository getInstance(Connection connection) {
        if (instance == null) {
            instance = new StockRepository(connection);
        }
        return instance;
    }

    /**
     * Retrieves all stock batches associated with a given item code.
     *
     * @param itemCode The code of the item.
     * @return A list of stock batches associated with the item.
     */
    public List<StockBatch> getStockBatchesByItemCode(String itemCode) {
        List<StockBatch> stockBatches = new ArrayList<>();
        String query = "SELECT * FROM Stock WHERE item_code = ? ORDER BY expiry_date ASC, purchase_date ASC";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, itemCode);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    StockBatch stockBatch = createStockBatchFromResultSet(rs);
                    stockBatches.add(stockBatch);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stockBatches;
    }

    /**
     * Updates the quantity of a stock batch.
     *
     * @param id       The ID of the stock batch.
     * @param quantity The new quantity value.
     */
    public void updateStockBatchQuantity(int id, int quantity) {
        String updateQuery = "UPDATE Stock SET quantity = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateQuery)) {
            pstmt.setInt(1, quantity);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes a stock batch from the database.
     *
     * @param id The ID of the stock batch to remove.
     */
    public void removeStockBatch(int id) {
        String deleteQuery = "DELETE FROM Stock WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(deleteQuery)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all stock batches.
     *
     * @return A list of all stock batches.
     */
    public List<StockBatch> findAllStockBatches() {
        List<StockBatch> stockBatches = new ArrayList<>();
        String query = "SELECT * FROM Stock ORDER BY expiry_date ASC, purchase_date ASC";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                StockBatch stockBatch = createStockBatchFromResultSet(rs);
                stockBatches.add(stockBatch);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stockBatches;
    }

    /**
     * Adds a new stock batch to the database.
     *
     * @param stockBatch The stock batch to add.
     */
    public void addStockBatch(StockBatch stockBatch) {
        String query = "INSERT INTO Stock (item_code, quantity, purchase_date, expiry_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, stockBatch.getItemCode());
            pstmt.setInt(2, stockBatch.getQuantity());
            pstmt.setDate(3, new Date(stockBatch.getPurchaseDate().getTime()));
            pstmt.setDate(4, new Date(stockBatch.getExpiryDate().getTime()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reduces the quantity of an item on the shelf.
     *
     * @param itemCode The code of the item to reduce quantity.
     * @param quantity The quantity to reduce.
     */
    public void reduceShelfQuantity(String itemCode, int quantity) {
        String query = "UPDATE Shelf SET quantity = quantity - ? WHERE item_code = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, quantity);
            pstmt.setString(2, itemCode);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds quantity of an item to the shelf.
     *
     * @param itemCode The code of the item to add to the shelf.
     * @param quantity The quantity to add.
     */
    public void addToShelf(String itemCode, int quantity) {
        String query = "INSERT INTO Shelf (item_code, quantity) VALUES (?, ?) ON DUPLICATE KEY UPDATE quantity = quantity + VALUES(quantity)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, itemCode);
            pstmt.setInt(2, quantity);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Finds items on the shelf below a specified threshold.
     *
     * @param threshold The threshold quantity.
     * @return A list of items below the specified threshold.
     */
    public List<Item> findItemsBelowThreshold(int threshold) {
        List<Item> itemsBelowThreshold = new ArrayList<>();
        String query = "SELECT * FROM Shelf WHERE quantity < ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, threshold);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Item item = createItemFromResultSet(rs);
                    itemsBelowThreshold.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemsBelowThreshold;
    }

    // Factory Method Pattern: Creates StockBatch objects from ResultSet
    private StockBatch createStockBatchFromResultSet(ResultSet rs) throws SQLException {
        return new StockBatch.StockBatchBuilder()
                .setId(rs.getInt("id"))
                .setItemCode(rs.getString("item_code"))
                .setQuantity(rs.getInt("quantity"))
                .setPurchaseDate(rs.getDate("purchase_date"))
                .setExpiryDate(rs.getDate("expiry_date"))
                .build();
    }

    // Factory Method Pattern: Creates Item objects from ResultSet
    private Item createItemFromResultSet(ResultSet rs) throws SQLException {
        return new Item.ItemBuilder()
                .setCode(rs.getString("item_code"))
                .setQuantity(rs.getInt("quantity"))
                .build();
    }
}
