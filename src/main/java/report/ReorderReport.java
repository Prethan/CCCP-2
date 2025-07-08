package report;

import model.Item;

import java.util.List;

/**
 * SOLID Principle used in ReorderReport class
 *
 * SRP: The ReorderReport class is responsible only for generating and displaying the reorder report.
 * OCP: The ReorderReport class can be extended with new methods for displaying the report in different formats without modifying existing code.
 * LSP: Not directly applicable as there are no subclasses.
 * ISP: Not directly applicable to this context as it’s a concrete class focusing on a single responsibility.
 * DIP: The ReorderReport class depends on the Item class, but this dependency is logical and straightforward, so DIP is not directly applicable.
 */

public class ReorderReport {
    private final List<Item> itemsBelowReorderLevel;

    public ReorderReport(List<Item> itemsBelowReorderLevel) {
        this.itemsBelowReorderLevel = itemsBelowReorderLevel;
    }

    public List<Item> getItemsBelowReorderLevel() {
        return itemsBelowReorderLevel;
    }

    public void display() {
        System.out.println("\nREORDER REPORT");
        System.out.println("------------------------------------------------------------------------------------------------------");
        System.out.println(String.format("%-20s %-20s %-20s ", "Item Code", "Item Name", "Price"));
        System.out.println("------------------------------------------------------------------------------------------------------");
        for (Item item : itemsBelowReorderLevel) {
            System.out.println(String.format("%-20s %-20s %-20.2f",
                    item.getCode(), item.getName(), item.getPrice()));
        }
        System.out.println("------------------------------------------------------------------------------------------------------");
    }
}
