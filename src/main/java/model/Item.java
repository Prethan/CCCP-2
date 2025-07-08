package model;

import java.io.Serializable;

/**
 * SOLID Principle used in Item model class
 *
 * SRP: Item class focuses only on representing an item.
 * OCP: Can be extended without modifying existing code.
 * LSP: Not applicable as there are no subclasses.
 * ISP: Not directly applicable to model classes.
 * DIP: Not directly applicable to model classes.
 */
public class Item implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code;
    private String name;
    private double price;
    private int quantity;
    private int restockAmount;

    // Private constructor to be used by the Builder
    private Item(ItemBuilder builder) {
        this.code = builder.code;
        this.name = builder.name;
        this.price = builder.price;
        this.quantity = builder.quantity;
        this.restockAmount = builder.restockAmount;
    }

    // Builder Pattern for constructing Item objects
    public static class ItemBuilder {
        private String code;
        private String name;
        private double price;
        private int quantity;
        private int restockAmount;

        public ItemBuilder setCode(String code) {
            this.code = code;
            return this;
        }

        public ItemBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public ItemBuilder setPrice(double price) {
            this.price = price;
            return this;
        }

        public ItemBuilder setQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public ItemBuilder setRestockAmount(int restockAmount) {
            this.restockAmount = restockAmount;
            return this;
        }

        public Item build() {
            return new Item(this);
        }
    }

    // Getter methods for Item class
    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getRestockAmount() {
        return restockAmount;
    }
}
