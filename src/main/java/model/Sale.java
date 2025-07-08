package model;

import java.io.Serializable;
import java.util.Date;

/**
 * SOLID Principle used in Sale model class
 *
 * SRP: Sale class focuses only on representing a sale.
 * OCP: Can be extended without modifying existing code.
 * LSP: Not applicable as there are no subclasses.
 * ISP: Not directly applicable to model classes.
 * DIP: Not directly applicable to model classes.
 */
public class Sale implements Serializable {
    private static final long serialVersionUID = 1L;

    private int salesId;
    private Date salesDate;
    private String itemCode;
    private int quantitySold;
    private double totalRevenue;

    // Private constructor to be used by the Builder
    public Sale(SaleBuilder builder) {
        this.salesId = builder.salesId;
        this.salesDate = builder.salesDate;
        this.itemCode = builder.itemCode;
        this.quantitySold = builder.quantitySold;
        this.totalRevenue = builder.totalRevenue;
    }

    // Builder Pattern
    public static class SaleBuilder {
        private int salesId;
        private Date salesDate;
        private String itemCode;
        private int quantitySold;
        private double totalRevenue;

        public SaleBuilder setSalesId(int salesId) {
            this.salesId = salesId;
            return this;
        }

        public SaleBuilder setSalesDate(Date salesDate) {
            this.salesDate = salesDate;
            return this;
        }

        public SaleBuilder setItemCode(String itemCode) {
            this.itemCode = itemCode;
            return this;
        }

        public SaleBuilder setQuantitySold(int quantitySold) {
            this.quantitySold = quantitySold;
            return this;
        }

        public SaleBuilder setTotalRevenue(double totalRevenue) {
            this.totalRevenue = totalRevenue;
            return this;
        }

        public Sale build() {
            return new Sale(this);
        }
    }

    // Getters
    public int getSalesId() {
        return salesId;
    }

    public Date getSalesDate() {
        return salesDate;
    }

    public String getItemCode() {
        return itemCode;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }
}
