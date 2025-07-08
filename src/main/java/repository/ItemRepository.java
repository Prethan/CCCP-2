package repository;

import model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * SOLID Principles used in ItemRepository class
 *
 * SRP: The ItemRepository class is responsible for database operations related to Item entities.
 * OCP: The ItemRepository class can be extended with additional query methods without modifying existing methods.
 * LSP: The class follows LSP as it can be replaced by any other implementation adhering to the same contract.
 * ISP: Not directly applicable as it's a concrete class focusing on a single responsibility.
 * DIP: The class depends on the abstract concept of a database connection rather than a specific implementation.
 */

// Singleton Pattern
public class ItemRepository {
    private static ItemRepository instance; // Singleton instance
    private Connection connection;

    // Singleton Pattern: Private constructor to prevent instantiation from outside
    private ItemRepository(Connection connection) {
        this.connection = connection;
    }

    // Singleton Pattern: Static method to get the singleton instance
    public static ItemRepository getInstance(Connection connection) {
        if (instance == null) {
            instance = new ItemRepository(connection);
        }
        return instance;
    }

    /**
     * Finds an item by its code.
     *
     * @param code The code of the item to find.
     * @return The item with the specified code, or null if not found.
     */
    public Item findItemByCode(String code) {
        String query = "SELECT * FROM Item WHERE code = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, code);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return createItemFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Finds items below a specified reorder level.
     *
     * @param reorderLevel The reorder level.
     * @return A list of items below the specified reorder level.
     */
    public List<Item> findItemsBelowReorderLevel(int reorderLevel) {
        List<Item> items = new ArrayList<>();
        String query = "SELECT i.code, i.name, i.price, SUM(s.quantity) AS total_quantity " +
                "FROM Item i " +
                "JOIN Stock s ON i.code = s.item_code " +
                "GROUP BY i.code, i.name, i.price " +
                "HAVING total_quantity < ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, reorderLevel);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Item item = createItemFromResultSet(rs);
                    items.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }



    /**
     * Gets an item by its code.
     *
     * @param itemCode The code of the item to get.
     * @return The item with the specified code, or null if not found.
     */
    public Item getItemByCode(String itemCode) {
        String query = "SELECT * FROM Item WHERE code = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, itemCode);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return createItemFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Factory Method Pattern: Creates Item objects from ResultSet
    private Item createItemFromResultSet(ResultSet rs) throws SQLException {
        return new Item.ItemBuilder()
                .setCode(rs.getString("code"))
                .setName(rs.getString("name"))
                .setPrice(rs.getDouble("price"))
                .build();
    }


}
