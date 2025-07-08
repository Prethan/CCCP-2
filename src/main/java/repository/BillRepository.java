package repository;

import model.Bill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * SOLID Principle used in BillingService service class
 *
 * SRP is emphasized by ensuring each class focuses on a single responsibility.
 * OCP is respected by allowing the classes to be extended without modifying existing code.
 * LSP is naturally adhered to by ensuring subclasses (if any) behave consistently with the base classes.
 * ISP isn't directly applicable here, but if there were larger interfaces, they would be split into smaller ones.
 * DIP is applied in BillingService by depending on abstractions (repositories and services) rather than concrete implementations.
 */
// Singleton Pattern
public class BillRepository {
    private static BillRepository instance; // Singleton instance
    private Connection connection;

    // Singleton Pattern: Private constructor to prevent instantiation from outside
    private BillRepository(Connection connection) {
        this.connection = connection;
    }

    // Singleton Pattern: Static method to get the singleton instance
    public static BillRepository getInstance(Connection connection) {
        if (instance == null) {
            instance = new BillRepository(connection);
        }
        return instance;
    }

    // Repository Pattern: Centralized interface for accessing and manipulating bill data
    public int getNextBillId() {
        String query = "SELECT MAX(bill_id) AS max_id FROM Bill";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt("max_id") + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1; // Start with 1 if no bills exist
    }

    // Unit of Work Pattern: Combining multiple operations within a single transaction
    public void saveBill(Bill bill) {
        String insertBillQuery = "INSERT INTO Bill (bill_id, bill_date, total_price, cash_tendered, change_amount) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertBillQuery)) {
            pstmt.setInt(1, bill.getBillId());
            pstmt.setDate(2, new Date(bill.getBillDate().getTime()));
            pstmt.setDouble(3, bill.getTotalPrice());
            pstmt.setDouble(4, bill.getCashTendered());
            pstmt.setDouble(5, bill.getChangeAmount());
            pstmt.executeUpdate();

            saveBillItems(bill.getBillId(), bill.getBillItems());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveBillItems(int billId, List<Bill.BillItem> billItems) {
        String insertBillItemQuery = "INSERT INTO BillItems (bill_id, item_code, quantity, total_price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertBillItemQuery)) {
            for (Bill.BillItem item : billItems) {
                pstmt.setInt(1, billId);
                pstmt.setString(2, item.getItemCode());
                pstmt.setInt(3, item.getQuantity());
                pstmt.setDouble(4, item.getTotalPrice());
                pstmt.addBatch();
            }
            // Batch Processing Pattern: Efficient insertion of multiple bill items in a single batch
            pstmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all bills from the database.
     *
     * @return a list of Bill objects.
     */
    public List<Bill> findAllBills() {
        List<Bill> bills = new ArrayList<>();
        String query = "SELECT * FROM Bill";

        // Execute the query and iterate over the result set
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int billId = rs.getInt("bill_id");

                // Lazy Loading Pattern: Bill items are fetched only when requested
                // Fetch the associated BillItems for each Bill
                List<Bill.BillItem> billItems = findBillItemsByBillId(billId);

                // Create Bill object using the Builder Pattern
                Bill bill = new Bill.BillBuilder()
                        .setBillId(billId)
                        .setBillDate(rs.getDate("bill_date"))
                        .setTotalPrice(rs.getDouble("total_price"))
                        .setCashTendered(rs.getDouble("cash_tendered"))
                        .setChangeAmount(rs.getDouble("change_amount"))
                        .build();

                // Add bill items to the bill
//                / Initialize BillBuilder with the existing Bill's properties
                Bill.BillBuilder billBuilder = new Bill.BillBuilder()
                        .setBillId(bill.getBillId())
                        .setBillDate(bill.getBillDate())
                        .setTotalPrice(bill.getTotalPrice())
                        .setCashTendered(bill.getCashTendered())
                        .setChangeAmount(bill.getChangeAmount());

                // Add each BillItem to the BillBuilder
                for (Bill.BillItem billItem : billItems) {
                    billBuilder.addBillItem(billItem);
                }

                // Build the final Bill
                bill = billBuilder.build();

                // Add the fully constructed Bill to the list
                bills.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bills;
    }

    /**
     * Retrieves all BillItems associated with a specific billId from the database.
     *
     * @param billId the ID of the bill whose items are to be retrieved.
     * @return a list of BillItem objects.
     */
    private List<Bill.BillItem> findBillItemsByBillId(int billId) {
        List<Bill.BillItem> billItems = new ArrayList<>();
        String query = "SELECT bi.*, i.name, i.price " +
                "FROM BillItems bi " +
                "JOIN Item i ON bi.item_code = i.code " +
                "WHERE bi.bill_id = ?";

        // Prepare and execute the query
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, billId);
            try (ResultSet rs = pstmt.executeQuery()) {
                // Iterate over the result set and create BillItem objects using the Factory Method Pattern
                while (rs.next()) {
                    Bill.BillItem billItem = Bill.BillItemFactory.createBillItem(
                            rs.getString("item_code"),
                            rs.getString("name"),
                            rs.getInt("quantity"),
                            rs.getDouble("price"),
                            rs.getDouble("total_price")
                    );
                    billItems.add(billItem);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return billItems;
    }
}
