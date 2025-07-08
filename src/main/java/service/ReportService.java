package service;

import model.Bill;
import model.Item;
import model.Sale;
import model.StockBatch;
import report.*;
import repository.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Singleton Pattern: Ensures only one instance of BillingService exists
public class ReportService {
    private static ReportService instance;
    private BillRepository billRepository;
    private SaleRepository saleRepository;
    private StockRepository stockRepository;
    private ItemRepository itemRepository;
    private ShelfRepository shelfRepository;

    // Singleton Pattern
    public ReportService(BillRepository billRepository, SaleRepository saleRepository, StockRepository stockRepository, ItemRepository itemRepository, ShelfRepository shelfRepository) {
        this.billRepository = billRepository;
        this.saleRepository = saleRepository;
        this.stockRepository = stockRepository;
        this.itemRepository = itemRepository;
        this.shelfRepository = shelfRepository;
    }

    public static ReportService getInstance(BillRepository billRepository, SaleRepository saleRepository, StockRepository stockRepository, ItemRepository itemRepository, ShelfRepository shelfRepository) {
        if (instance == null) {
            instance = new ReportService(billRepository, saleRepository, stockRepository, itemRepository, shelfRepository);
        }
        return instance;
    }

    /**
     * Generates a report containing information about all bills.
     *
     * @return the BillReport object containing bill information.
     */
    public BillReport generateBillReport() {
        List<Bill> bills = billRepository.findAllBills();
        return new BillReport(bills);
    }

    /**
     * Generates a report containing daily sales information for a given date.
     *
     * @param date the date for which the report is generated.
     * @return the DailySalesReport object containing daily sales information.
     */
    public DailySalesReport generateDailySalesReport(Date date) {
        List<Sale> sales = saleRepository.findSalesByDate(date);
        Map<String, DailySalesReport.SalesSummary> salesSummaryMap = new HashMap<>();

        for (Sale sale : sales) {
            String itemCode = sale.getItemCode();
            DailySalesReport.SalesSummary summary = salesSummaryMap.getOrDefault(itemCode,
                    new DailySalesReport.SalesSummary(itemCode, itemRepository.getItemByCode(itemCode).getName()));
            summary.addQuantity(sale.getQuantitySold());
            summary.addRevenue(sale.getTotalRevenue());
            salesSummaryMap.put(itemCode, summary);
        }

        return new DailySalesReport(salesSummaryMap);
    }

    /**
     * Generates a report containing items that are below the threshold on shelves.
     *
     * @return the ReshelvingReport object containing items below the threshold.
     */
    public ReshelvingReport generateReshelvingReport() {
        List<Item> itemsBelowThreshold = shelfRepository.findItemsBelowThreshold(20);
        return new ReshelvingReport(itemsBelowThreshold);
    }

    /**
     * Generates a report containing items that need to be reordered due to low stock levels.
     *
     * @return the ReorderReport object containing items to be reordered.
     */
    public ReorderReport generateReorderReport() {
        List<Item> items = itemRepository.findItemsBelowReorderLevel(50);
        return new ReorderReport(items);
    }

    /**
     * Generates a report containing information about stock batches.
     *
     * @return a list of StockReport objects containing stock information.
     */
    public List<StockReport> generateStockReport() {
        List<StockBatch> stockBatches = stockRepository.findAllStockBatches();
        Map<Date, List<StockBatch>> batchesByPurchaseDate = groupBatchesByPurchaseDate(stockBatches);
        return createStockReports(batchesByPurchaseDate);
    }

    private Map<Date, List<StockBatch>> groupBatchesByPurchaseDate(List<StockBatch> stockBatches) {
        Map<Date, List<StockBatch>> batchesByPurchaseDate = new HashMap<>();
        for (StockBatch batch : stockBatches) {
            Date purchaseDate = (Date) batch.getPurchaseDate();
            batchesByPurchaseDate.computeIfAbsent(purchaseDate, k -> new ArrayList<>()).add(batch);
        }
        return batchesByPurchaseDate;
    }

    private List<StockReport> createStockReports(Map<Date, List<StockBatch>> batchesByPurchaseDate) {
        List<StockReport> stockReports = new ArrayList<>();
        for (Map.Entry<Date, List<StockBatch>> entry : batchesByPurchaseDate.entrySet()) {
            StockReport stockReport = new StockReport(entry.getValue());
            stockReports.add(stockReport);
        }

        return stockReports;
    }

    public void displayStockReports(List<StockReport> stockReports) {
        for (StockReport stockReport : stockReports) {
            stockReport.display();
        }
    }
}
