package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * SOLID Principle used in Bill model class
 *
 * SRP: Bill class focuses only on representing a bill.
 * OCP: Can be extended without modifying existing code.
 * LSP: Not applicable as there are no subclasses.
 * ISP: Not directly applicable to model classes.
 * DIP: Not directly applicable to model classes.
 */
public class Bill implements Serializable {

    private static final long serialVersionUID = 1L;

    private int billId;
    private Date billDate;
    private double totalPrice;
    private double cashTendered;
    private double changeAmount;
    private List<BillItem> billItems;

    // Private constructor to be used by the Builder
    private Bill(BillBuilder builder) {
        this.billId = builder.billId;
        this.billDate = builder.billDate;
        this.totalPrice = builder.totalPrice;
        this.cashTendered = builder.cashTendered;
        this.changeAmount = builder.changeAmount;
        this.billItems = builder.billItems;
    }

    // Builder Pattern for constructing Bill objects
    public static class BillBuilder {
        private int billId;
        private Date billDate;
        private double totalPrice;
        private double cashTendered;
        private double changeAmount;
        private List<BillItem> billItems = new ArrayList<>();

        public BillBuilder setBillId(int billId) {
            this.billId = billId;
            return this;
        }

        public BillBuilder setBillDate(Date billDate) {
            this.billDate = billDate;
            return this;
        }

        public BillBuilder setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public BillBuilder setCashTendered(double cashTendered) {
            this.cashTendered = cashTendered;
            return this;
        }

        public BillBuilder setChangeAmount(double changeAmount) {
            this.changeAmount = changeAmount;
            return this;
        }

        public BillBuilder addBillItem(BillItem billItem) {
            this.billItems.add(billItem);
            return this;
        }

        public Bill build() {
            return new Bill(this);
        }
    }

    // Getter methods for Bill class
    public int getBillId() {
        return billId;
    }

    public Date getBillDate() {
        return billDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public double getCashTendered() {
        return cashTendered;
    }

    public double getChangeAmount() {
        return changeAmount;
    }

    public List<BillItem> getBillItems() {
        return billItems;
    }

    // BillItem class representing items in the bill
    public static class BillItem implements Serializable {
        private static final long serialVersionUID = 1L;

        private String itemCode;
        private String itemName;
        private int quantity;
        private double unitPrice;
        private double totalPrice;

        // Constructors
        private BillItem(String itemCode, String itemName, int quantity, double totalPrice) {
            this.itemCode = itemCode;
            this.itemName = itemName;
            this.quantity = quantity;
            this.totalPrice = totalPrice;
        }

        private BillItem(String itemCode, int quantity, double unitPrice, double totalPrice) {
            this.itemCode = itemCode;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
            this.totalPrice = totalPrice;
        }

        public BillItem(String itemCode, String itemName, int quantity, double unitPrice, double totalPrice) {
            this.itemCode = itemCode;
            this.itemName = itemName;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
            this.totalPrice = totalPrice;
        }

        // Getter and setter methods
        public String getItemCode() {
            return itemCode;
        }

        public String getItemName() {
            return itemName;
        }

        public int getQuantity() {
            return quantity;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public double getUnitPrice() {
            return unitPrice;
        }
    }

    // Factory Method Pattern for creating BillItem objects
    public static class BillItemFactory {
        public static BillItem createBillItem(String itemCode, String itemName, int quantity, double totalPrice) {
            return new BillItem(itemCode, itemName, quantity, totalPrice);
        }
        public static BillItem createBillItem(String itemCode, int quantity, double unitPrice, double totalPrice) {
            return new BillItem(itemCode, quantity, unitPrice, totalPrice);
        }

        public static BillItem createBillItem(String itemCode, String itemName, int quantity, double unitPrice, double totalPrice) {
            return new BillItem(itemCode, itemName, quantity, unitPrice, totalPrice);
        }
    }
}
