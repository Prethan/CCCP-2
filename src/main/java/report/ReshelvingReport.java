package report;

import model.Item;

import java.util.List;

/**
 * SOLID Principle used in ReshelvingReport class
 *
 * SRP: The ReshelvingReport class is responsible only for generating and displaying the reshelving report.
 * OCP: The ReshelvingReport class can be extended with new methods for displaying the report in different formats without modifying existing code.
 * LSP: Not directly applicable as there are no subclasses.
 * ISP: Not directly applicable to this context as it’s a concrete class focusing on a single responsibility.
 * DIP: The ReshelvingReport class depends on the Item class, but this dependency is logical and straightforward, so DIP is not directly applicable.
 */

public class ReshelvingReport {
    private List<Item> items;

    public ReshelvingReport(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public void display() {
        System.out.println("\nRESHELVING REPORT");
        System.out.println("-------------------------------------------------------------------");
        System.out.println(String.format("%-20s %-26s %-20s", "Item Code", "Available Quantity", "Restock Amount"));
        System.out.println("-------------------------------------------------------------------");
        for (Item item : items) {
            System.out.println(String.format("%-20s %-26s %-20d",
                    item.getCode(), item.getQuantity(), 20 - item.getQuantity()));
        }
        System.out.println("-------------------------------------------------------------------");

    }
}
