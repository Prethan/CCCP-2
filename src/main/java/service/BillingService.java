package service;

import model.Bill;
import model.Item;
import model.Sale;
import repository.BillRepository;
import repository.ItemRepository;
import repository.SaleRepository;

import java.util.Date;
import java.util.List;

/**
 * SOLID Principle used in BillingService service class
 *
 * SRP: Handles only billing-related operations.
 * OCP: Can be extended with new features without modifying existing methods.
 * LSP: Ensure methods can be overridden without breaking the class behavior.
 * ISP: Break down large service interfaces into smaller, more specific ones.
 * DIP: Depend on abstractions (Repository interfaces) rather than concrete classes.
 */
// Singleton Pattern: Ensures only one instance of BillingService exists
public class BillingService {
    private static BillingService instance;

    private BillRepository billRepository;
    private ItemRepository itemRepository;
    private StockService stockService;
    private SaleRepository saleRepository;
    private ShelfService shelfService;

    // Private constructor to prevent instantiation from outside
    private BillingService(BillRepository billRepository, ItemRepository itemRepository,
                           StockService stockService, SaleRepository saleRepository,
                           ShelfService shelfService) {
        this.billRepository = billRepository;
        this.itemRepository = itemRepository;
        this.stockService = stockService;
        this.saleRepository = saleRepository;
        this.shelfService = shelfService;
    }

    // Static method to get the instance of BillingService
    public static BillingService getInstance(BillRepository billRepository, ItemRepository itemRepository,
                                             StockService stockService, SaleRepository saleRepository,
                                             ShelfService shelfService) {
        if (instance == null) {
            instance = new BillingService(billRepository, itemRepository, stockService, saleRepository, shelfService);
        }
        return instance;
    }

    /**
     * Creates a new Bill, updates the shelf, and records the sales.
     *
     * @param billItems     the list of BillItem objects for the bill.
     * @param cashTendered  the amount of cash tendered by the customer.
     * @return the created Bill object.
     */
    // Facade Pattern: Provides a simplified interface to complex operations
    public Bill createBill(List<Bill.BillItem> billItems, double cashTendered) {
        // Calculate the total price of the bill
        double totalPrice = billItems.stream().mapToDouble(Bill.BillItem::getTotalPrice).sum();
        double changeAmount = cashTendered - totalPrice;

        // Generate the next bill ID
        int billId = billRepository.getNextBillId();

        // Create the Bill object using the Builder Pattern
        Bill bill = new Bill.BillBuilder()
                .setBillId(billId)
                .setBillDate(new Date())
                .setTotalPrice(totalPrice)
                .setCashTendered(cashTendered)
                .setChangeAmount(changeAmount)
                .build();

        // Initialize BillBuilder with the existing Bill's properties
        Bill.BillBuilder billBuilder = new Bill.BillBuilder()
                .setBillId(bill.getBillId())
                .setBillDate(bill.getBillDate())
                .setTotalPrice(bill.getTotalPrice())
                .setCashTendered(bill.getCashTendered())
                .setChangeAmount(bill.getChangeAmount());

        // Add each BillItem to the BillBuilder
        for (Bill.BillItem billItem : billItems) {
            billBuilder.addBillItem(billItem);
        }

        // Build the final Bill
        bill = billBuilder.build();

        // Save the Bill to the repository
        billRepository.saveBill(bill);

        // Reduce items from shelf after generating the bill
        for (Bill.BillItem billItem : billItems) {
            shelfService.reduceShelf(billItem.getItemCode(), billItem.getQuantity());
        }

        // Record each item sale in the Sales table using Builder Pattern
        for (Bill.BillItem billItem : billItems) {
            Sale sale = new Sale.SaleBuilder()
                    .setSalesId(0)
                    .setSalesDate(new Date())
                    .setItemCode(billItem.getItemCode())
                    .setQuantitySold(billItem.getQuantity())
                    .setTotalRevenue(billItem.getTotalPrice())
                    .build();
            saleRepository.addSale(sale);
        }

        return bill;
    }

    /**
     * Creates a new BillItem.
     *
     * @param itemCode  the code of the item.
     * @param quantity  the quantity of the item.
     * @param unitPrice the unit price of the item.
     * @return the created BillItem object.
     */
    // Strategy Pattern: Encapsulates the creation of BillItem objects, allowing for interchangeable creation strategies
    public Bill.BillItem createBillItem(String itemCode, int quantity, double unitPrice) {
        // Find the item by its code
        Item item = itemRepository.findItemByCode(itemCode);

        // Calculate the total price
        double totalPrice = item.getPrice() * quantity;

        // Create and return the BillItem using the Factory Method Pattern
        return Bill.BillItemFactory.createBillItem(itemCode, item.getName(), quantity, unitPrice, totalPrice);
    }
}
