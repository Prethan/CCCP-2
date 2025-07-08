package repository;

import model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * SOLID Principles used in ShelfRepository class
 *
 * SRP: The ShelfRepository class is responsible for database operations related to Shelf entities.
 * OCP: The ShelfRepository class can be extended with additional query methods without modifying existing methods.
 * LSP: The class follows LSP as it can be replaced by any other implementation adhering to the same contract.
 * ISP: Not directly applicable as it's a concrete class focusing on a single responsibility.
 * DIP: The class depends on the abstract concept of a database connection rather than a specific implementation.
 */

// Singleton Pattern
public class ShelfRepository {
    private static ShelfRepository instance; // Singleton instance
    private Connection connection;

    // Singleton Pattern: Private constructor to prevent instantiation from outside
    private ShelfRepository(Connection connection) {
        this.connection = connection;
    }

    // Singleton Pattern: Static method to get the singleton instance
    public static ShelfRepository getInstance(Connection connection) {
        if (instance == null) {
            instance = new ShelfRepository(connection);
        }
        return instance;
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
        String query = "UPDATE Shelf SET quantity = quantity + ? WHERE item_code = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, quantity);
            pstmt.setString(2, itemCode);
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

    // Factory Method Pattern: Creates Item objects from ResultSet
    private Item createItemFromResultSet(ResultSet rs) throws SQLException {
        return new Item.ItemBuilder()
                .setCode(rs.getString("item_code"))
                .setQuantity(rs.getInt("quantity"))
                .build();
    }
}
