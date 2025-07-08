package model;

import java.io.Serializable;
import java.util.Date;

/**
 * SOLID Principle used in StockBatch model class
 *
 * SRP: StockBatch class focuses only on representing a stock batch.
 * OCP: Can be extended without modifying existing code.
 * LSP: Not applicable as there are no subclasses.
 * ISP: Not directly applicable to model classes.
 * DIP: Not directly applicable to model classes.
 */
public class StockBatch implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String itemCode;
    private int quantity;
    private Date purchaseDate;
    private Date expiryDate;

    // Private constructor to be used by the Builder
    private StockBatch(StockBatchBuilder builder) {
        this.id = builder.id;
        this.itemCode = builder.itemCode;
        this.quantity = builder.quantity;
        this.purchaseDate = builder.purchaseDate;
        this.expiryDate = builder.expiryDate;
    }

    // Builder Pattern for constructing StockBatch objects
    public static class StockBatchBuilder {
        private int id;
        private String itemCode;
        private int quantity;
        private Date purchaseDate;
        private Date expiryDate;

        public StockBatchBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public StockBatchBuilder setItemCode(String itemCode) {
            this.itemCode = itemCode;
            return this;
        }

        public StockBatchBuilder setQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public StockBatchBuilder setPurchaseDate(Date purchaseDate) {
            this.purchaseDate = purchaseDate;
            return this;
        }

        public StockBatchBuilder setExpiryDate(Date expiryDate) {
            this.expiryDate = expiryDate;
            return this;
        }

        public StockBatch build() {
            return new StockBatch(this);
        }
    }

    // Getter methods for StockBatch class
    public int getId() {
        return id;
    }

    public String getItemCode() {
        return itemCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }
}
