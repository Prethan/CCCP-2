package report;

import model.StockBatch;

import java.util.List;

/**
 * SOLID Principles used in StockReport class
 *
 * SRP: The StockReport class is responsible only for generating and displaying stock reports.
 * OCP: The StockReport class can be extended with new methods for displaying the report in different formats without modifying existing code.
 * LSP: Not directly applicable as there are no subclasses.
 * ISP: Not directly applicable to this context as it’s a concrete class focusing on a single responsibility.
 * DIP: The StockReport class depends on the StockBatch class, but this dependency is logical and straightforward, so DIP is not directly applicable.
 */



public class StockReport {
    private List<StockBatch> stockBatches;

    public StockReport(List<StockBatch> stockBatches) {
        this.stockBatches = stockBatches;
    }

    public List<StockBatch> getStockBatches() {
        return stockBatches;
    }

    public void display() {
        System.out.println("\nSTOCK REPORT FOR BATCH : " + stockBatches.get(0).getPurchaseDate().toString());
        String format = " %-10s %-10s %-15s %-15s %n";
        System.out.println("--------------------------------------------------------");
        System.out.printf(" %-10s %-10s %-15s %-15s %n", "Item Code", "Quantity", "Purchase Date", "Expiry Date");
        System.out.println("--------------------------------------------------------");

        for (StockBatch batch : stockBatches) {
            System.out.printf(format,
                    batch.getItemCode(),
                    batch.getQuantity(),
                    batch.getPurchaseDate().toString(),
                    batch.getExpiryDate().toString());
        }

        System.out.println("--------------------------------------------------------");
    }

}
