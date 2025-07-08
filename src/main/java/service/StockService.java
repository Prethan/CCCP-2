package service;

import model.StockBatch;
import repository.StockRepository;

import java.util.List;

public class StockService {
    private static StockService instance;
    private StockRepository stockRepository;

    // Singleton Pattern: Private constructor to prevent instantiation from outside
    private StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    // Singleton Pattern: Provides a global point of access to the singleton instance
    public static synchronized StockService getInstance(StockRepository stockRepository) {
        if (instance == null) {
            instance = new StockService(stockRepository);
        }
        return instance;
    }

    /**
     * Encapsulates the request to reduce the quantity of an item in the stock.
     *
     * @param itemCode the code of the item to reduce the quantity for.
     * @param quantity the quantity to reduce.
     *
     */
    // Command Pattern: Encapsulates the request to reduce stock
    public void reduceStock(String itemCode, int quantity) {
        List<StockBatch> stockBatches = stockRepository.getStockBatchesByItemCode(itemCode);
        for (StockBatch batch : stockBatches) {
            if (quantity <= 0) {
                break;
            }

            int availableQuantity = batch.getQuantity();
            if (availableQuantity >= quantity) {
                stockRepository.updateStockBatchQuantity(batch.getId(), availableQuantity - quantity);
                quantity = 0;
            } else {
                stockRepository.removeStockBatch(batch.getId());
                quantity -= availableQuantity;
            }
        }

        if (quantity > 0) {
            throw new RuntimeException("Not enough stock available for item: " + itemCode);
        }
    }

    /**
     * Encapsulates the request to add a new stock batch.
     *
     * @param stockBatch the stock batch to be added.
     *
     */
    // Command Pattern: Encapsulates the request to add a stock batch
    public void addStockBatch(StockBatch stockBatch) {
        System.out.println("Inside Add stock function");
        stockRepository.addStockBatch(stockBatch);
    }
}
