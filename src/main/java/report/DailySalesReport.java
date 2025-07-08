package report;

import java.util.Map;

/**
 * SOLID Principle used in DailySalesReport class
 *
 * SRP: The DailySalesReport class is responsible only for generating and displaying the daily sales report.
 * OCP: The DailySalesReport class can be extended with new methods for displaying the report in different formats without modifying existing code.
 * LSP: Not directly applicable as there are no subclasses.
 * ISP: Not directly applicable to this context as it’s a concrete class focusing on a single responsibility.
 * DIP: The DailySalesReport class depends on the SalesSummary class, but this dependency is logical and straightforward, so DIP is not directly applicable.
 */

public class DailySalesReport {
    private final Map<String, SalesSummary> salesSummary;

    public DailySalesReport(Map<String, SalesSummary> salesSummary) {
        this.salesSummary = salesSummary;
    }

    public Map<String, SalesSummary> getSalesSummary() {
        return salesSummary;
    }

    public void display() {
        System.out.println("\nDAILY SALES REPORT");
        System.out.println("-------------------------------------------------------------");
        System.out.println(String.format("%-20s %-10s %-10s %-10s", "Item Name", "Item Code", "Quantity", "Revenue"));
        System.out.println("-------------------------------------------------------------");
        for (SalesSummary summary : salesSummary.values()) {
            System.out.println(String.format("%-20s %-10s %-10d %-10.2f",
                    summary.getItemName(), summary.getItemCode(), summary.getTotalQuantity(), summary.getTotalRevenue()));
        }
        System.out.println("-------------------------------------------------------------");

    }


    public static class SalesSummary {
        private final String itemCode;
        private final String itemName;
        private int totalQuantity;
        private double totalRevenue;

        public SalesSummary(String itemCode, String itemName) {
            this.itemCode = itemCode;
            this.itemName = itemName;
            this.totalQuantity = 0;
            this.totalRevenue = 0.0;
        }

        public void addQuantity(int quantity) {
            this.totalQuantity += quantity;
        }

        public void addRevenue(double revenue) {
            this.totalRevenue += revenue;
        }

        public String getItemCode() {
            return itemCode;
        }

        public String getItemName() {
            return itemName;
        }

        public int getTotalQuantity() {
            return totalQuantity;
        }

        public double getTotalRevenue() {
            return totalRevenue;
        }
    }
}
