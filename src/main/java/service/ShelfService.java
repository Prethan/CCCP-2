package service;

import model.Item;
import repository.ShelfRepository;
import repository.StockRepository;

import java.util.List;

public class ShelfService {
    private static ShelfService instance; // Singleton instance

    private StockRepository stockRepository;
    private StockService stockService;
    private ShelfRepository shelfRepository;

    // Private constructor to prevent instantiation from outside
    private ShelfService(StockRepository stockRepository, StockService stockService, ShelfRepository shelfRepository) {
        this.stockRepository = stockRepository;
        this.stockService = stockService;
        this.shelfRepository = shelfRepository;
    }

    // Singleton getInstance method to provide global access to the instance
    public static ShelfService getInstance(StockRepository stockRepository, StockService stockService, ShelfRepository shelfRepository) {
        if (instance == null) {
            instance = new ShelfService(stockRepository, stockService, shelfRepository);
        }
        return instance;
    }

    /**
     * Encapsulates the request to reduce the quantity of an item on the shelf.
     *
     * @param itemCode the code of the item to reduce the quantity for.
     * @param quantity the quantity to reduce.
     *
     */
    // Command Pattern: Encapsulates the request to reduce the quantity of an item on the shelf
    public void reduceShelf(String itemCode, int quantity) {
        // Facade Pattern: Provides a simplified interface to interact with the shelf subsystem
        shelfRepository.reduceShelfQuantity(itemCode, quantity);
    }

    /**
     * Restocks items below the threshold level.
     *
     * @return
     */
    public List<Item> reshelfItems() {
        List<Item> itemsBelowThreshold = stockRepository.findItemsBelowThreshold(20); // Get items below threshold
        System.out.println("\n-----------------------------------------------");
        System.out.println("The following items have been restocked.");
        for (Item item : itemsBelowThreshold) {
            System.out.println("Item " + item.getCode() + " : " + (20 - item.getQuantity()) + " units added");
            int quantityToAdd = 20 - item.getQuantity(); // Calculate quantity needed to reach threshold
            if (quantityToAdd > 0) {
                stockService.reduceStock(item.getCode(), quantityToAdd); // Reduce from stock
                shelfRepository.addToShelf(item.getCode(), quantityToAdd); // Add to shelf
            }
        }
        System.out.println("-----------------------------------------------\n");
        return itemsBelowThreshold;
    }

}
