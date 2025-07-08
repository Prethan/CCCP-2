package report;

import model.Bill;

import java.util.List;

/**
 * SOLID Principle used in BillReport class
 *
 * SRP: The BillReport class is responsible only for generating and displaying the bill report.
 * OCP: The BillReport class can be extended with new methods for displaying the report in different formats without modifying existing code.
 * LSP: Not directly applicable as there are no subclasses.
 * ISP: Not directly applicable to this context as it’s a concrete class focusing on a single responsibility.
 * DIP: Not directly applicable since this class is not dependent on abstractions.
 */

public class BillReport {
    private List<Bill> bills;

    public BillReport(List<Bill> bills) {
        this.bills = bills;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public void display() {
            System.out.println("\nBILL REPORT");
        for (Bill bill : bills) {
            System.out.println("\n===============================================================");
            System.out.println("Bill ID: " + bill.getBillId());
            System.out.println("Bill Date: " + bill.getBillDate());
            System.out.println("\n---------------------------------------------------------------");
            System.out.printf("%-20s %-10s %-12s %-10s%n", "Item Name", "Quantity", "Unit Price", "Total Price");
            System.out.println("---------------------------------------------------------------");
            for (Bill.BillItem item : bill.getBillItems()) {
                System.out.printf("%-20s %-10d %-12.2f %-10.2f%n",
                        item.getItemName(), item.getQuantity(), item.getUnitPrice(), item.getTotalPrice());
            }
            System.out.println("---------------------------------------------------------------\n");
            System.out.printf("%-42s %-10.2f%n", "Total Price:", bill.getTotalPrice());
            System.out.printf("%-42s %-10.2f%n", "Cash Tendered:", bill.getCashTendered());
            System.out.printf("%-42s %-10.2f%n", "Change Amount:", bill.getChangeAmount());
            System.out.println("===============================================================\n\n");
        }
    }
}
