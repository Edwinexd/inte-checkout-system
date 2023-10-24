import com.agie.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReceiptTest {
    public Receipt getStandardReceipt(){
        return new Receipt(1, getStandardCustomer());
    }
    public Customer getStandardCustomer(){
        return new Customer(200001010001l);
    }

    public Receipt getReceiptWithoutCustomer() {
        return new Receipt(1, null);
    }
    
    public Receipt getReceiptWithYoungCustomer() {
        return new Receipt(1, new Customer(201001010001l));
    }

    

    public Item getStandardItem(){
        ItemCategory fruit = new ItemCategory("Fruit", VATRate.VAT_12, null);
        Supplier supplier = new Supplier("Supplier");
        return new Item("Apple", new Money(1, Currency.SEK), fruit, null, supplier, null, true);
    }

    public Item getItemWithEighteenAgeLimit(){
        ItemCategory alcohol = new ItemCategory("Alcohol", VATRate.VAT_25, AgeLimit.AGE_LIMIT_18);
        Supplier supplier = new Supplier("Supplier");
        return new Item("Beer", new Money(1, Currency.SEK), alcohol, null, supplier, null, true);
    }

    private Payment getStandardPayment() {
        return new Payment(new Money(1, Currency.SEK), PaymentType.CASH);
    }

    public ItemRow getStandardItemRow(){
        return new ItemRow(getStandardItem(), 1);
    }

    @Test
    public void createReceipt(){
        Receipt r = getStandardReceipt();
        assertNotNull(r);
    }

    @Test
    public void receiptDateNotNull(){
        Receipt r = getStandardReceipt();
        assertNotNull(r.getDate());
    }

    @Test
    public void receiptIdCantBeLessThanOne(){
        assertThrows(IllegalArgumentException.class, () ->{
            new Receipt(-1,getStandardCustomer());
        });
    }

    @Test
    public void receiptAddItemDoesNotThrow(){
        Receipt receipt = getStandardReceipt();
        ItemRow row = getStandardItemRow();

        assertDoesNotThrow(() -> receipt.addItem(row.getItem(), row.getQuantity()));
    }

    @Test
    public void receiptReturnsPnr(){
        Customer c = getStandardCustomer();

        assertEquals(200001010001l, c.getPnr());
    }

    @Test
    public void receiptTotalReturnZero(){
        Receipt r = getStandardReceipt();
        assertEquals(new Money(0, Currency.SEK).getAmount(), r.getTotal());
    }

    @Test
    public void receiptAddOnePayment(){ // TODO: r.GetPayments returns an arraylist of payments, not a single payment?
        Receipt r = getStandardReceipt();
        Payment p = getStandardPayment();

        r.addPayment(p);
        assertTrue(r.getPayments().contains(p));
    }

    @Test
    public void receiptAddMultiplePayment(){
        Receipt r = getStandardReceipt();
        Payment p1 = getStandardPayment();
        Payment p2 = getStandardPayment();
        Payment p3 = getStandardPayment();

        ArrayList<Payment> listOfPayments = new ArrayList<>();

        listOfPayments.add(p1);
        listOfPayments.add(p2);
        listOfPayments.add(p3);

        r.addPayment(p1);
        r.addPayment(p2);
        r.addPayment(p3);
        assertEquals(listOfPayments, r.getPayments());
    }

    @Test
    public void receiptAddNullPayment(){
        Receipt r = getStandardReceipt();
        Payment p = null;

        assertThrows(IllegalArgumentException.class, () -> r.addPayment(p));
    }

    @Test
    public void addItemNullThrows(){
        Receipt r = getStandardReceipt();
        Item item = null;
        double quantity = 1;

        assertThrows(IllegalArgumentException.class, () -> r.addItem(item, quantity));
    }


/*
Måste fixa så att vi kan nå private metod
    @Test
    public void addItemTest(){


        Receipt r = getStandardReceipt();
        Item item = getStandardItem();
        double quantity = 1;

        r.addItem(item, quantity);



        List<ItemRow> itemRows = new ArrayList<>();
        itemRows.add(new ItemRow(item, quantity));
        //TODO: Look for ClassUnderTest of jUnit to access private class variables.
        assertIterableEquals(itemRows, r.getItemRowHolder().values());
    }

 */



    @Test
    public void addItemMultipleTimesIncreasesQuantity(){
        Receipt r = getStandardReceipt();
        Item item = getStandardItem();
        double quantity = 1;

        r.addItem(item, quantity);
        r.addItem(item, quantity);
        r.addItem(item, quantity);

        assertEquals(3d, r.getItemRow(item).getQuantity());
    }

    @Test
    public void addItemMultipleTimesToZeroRemovesRow(){
        Receipt r = getStandardReceipt();
        Item item = getStandardItem();

        r.addItem(item, 1);
        r.addItem(item, 2);
        r.addItem(item, -3);

        assertNull(r.getItemRow(item));
    }


    @Test
    public void receiptIsPaid(){ //TODO: FIX
        Receipt r = getStandardReceipt();
        Payment p = getStandardPayment();
        r.addPayment(p);

        assertTrue(r.isPaid());
    }

    @Test
    public void receiptIsNotPaid(){
        Receipt r = getStandardReceipt();

        assertFalse(r.isPaid());
    }


    // TODO Fix gusten
    /*@Test
    public void receiptGetChange(){ // TODO: FIX
        Receipt r = getStandardReceipt();
        Payment p = getStandardPayment();

        r.addPayment(p);
        assertEquals(0, r.getChange());
    }

    @Test
    public void receiptGetChangeMultiplePayments(){
        Receipt r = getStandardReceipt();
        Payment p1 = getStandardPayment();
        Payment p2 = getStandardPayment();
        Payment p3 = getStandardPayment();

        r.addPayment(p1);
        r.addPayment(p2);
        r.addPayment(p3);
        assertEquals(0, r.getChange()); // TODO: FIX
    }*/

    // Test addItem with item that has a category that has a age limit
    // if the customer is under the age limit, the item should not be added and a IllegalArgumentException should be thrown

    @Test
    public void addItemWithAgeLimitCustomerNull(){
        Receipt r = getReceiptWithoutCustomer();
        Item item = getItemWithEighteenAgeLimit();
        double quantity = 1;

        assertThrows(IllegalArgumentException.class, () -> r.addItem(item, quantity));
    }

    @Test
    public void addItemWithAgeLimitCustomerUnderAge(){
        Receipt r = getReceiptWithYoungCustomer();
        Item item = getItemWithEighteenAgeLimit();
        double quantity = 1;

        assertThrows(IllegalArgumentException.class, () -> r.addItem(item, quantity));
    }

    @Test
    public void addItemWithAgeLimitCustomerHasAge(){
        Receipt r = getStandardReceipt();
        Item item = getItemWithEighteenAgeLimit();
        double quantity = 1;

        r.addItem(item, quantity);
    }
}
