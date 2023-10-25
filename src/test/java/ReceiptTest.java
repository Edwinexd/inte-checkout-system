import com.agie.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ReceiptTest {
    // Helper methods
    public Receipt getStandardReceipt() {
        return new Receipt(1, getStandardCustomer());
    }

    public Customer getStandardCustomer() {
        return new Customer(200001010001l);
    }

    public Item getStandardItem() {
        ItemCategory fruit = new ItemCategory("Fruit", VATRate.VAT_12, null);
        Supplier supplier = new Supplier("Supplier");
        return new Item("Apple", new Money(1, Currency.SEK), fruit, null, supplier, null, true);
    }

    private Payment getStandardPayment() {
        return new Payment(new Money(1, Currency.SEK), PaymentType.CASH);
    }

    public ItemRow getStandardItemRow() {
        return new ItemRow(getStandardItem(), 1);
    }

    // Tests start here

    @Test
    public void receiptNotNull() {
        assertNotNull(getStandardReceipt());
    }

    @Test
    public void receiptConstructor() {
        assertDoesNotThrow(() -> getStandardReceipt());
    }

    @Test
    public void receiptGetId() {
        Receipt r = getStandardReceipt();
        assertEquals(1, r.getId());
    }

    @Test
    public void receiptDateNotNull() {
        Receipt r = getStandardReceipt();
        assertNotNull(r.getDate());
    }

    @Test
    public void receiptCorrectDateDatatype() {
        Receipt r = getStandardReceipt();

        assertTrue(r.getDate() instanceof Date);
    }

    // Date starts counting years from 1900
    @Test
    public void receiptCorrectYear() {
        Receipt r = getStandardReceipt();
        assertEquals(Calendar.getInstance().get(Calendar.YEAR), r.getDate().getYear() + 1900);
    }

    @Test
    public void receiptCorrectMonth() {
        Receipt r = getStandardReceipt();
        assertEquals(Calendar.getInstance().get(Calendar.MONTH), r.getDate().getMonth());
    }

    @Test
    public void receiptCorrectDay() {
        Receipt r = getStandardReceipt();
        Calendar c = Calendar.getInstance();
        assertEquals(c.get(Calendar.DAY_OF_MONTH), r.getDate().getDate());
    }

    @Test
    public void getCustomer() {
        Customer c = new Customer(2000_01_01_0001L);
        Receipt r = new Receipt(1, c);
        assertEquals(c, r.getCustomer());
    }

    @Test
    public void receiptIdCantBeLessThanOne() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Receipt(-1, getStandardCustomer());
        });
    }

    @Test
    public void receiptAddItemDoesNotThrow() {
        Receipt receipt = getStandardReceipt();
        ItemRow row = getStandardItemRow();

        assertDoesNotThrow(() -> receipt.addItem(row.getItem(), row.getQuantity()));
    }

    @Test
    public void addItemZeroQuantity() {
        Receipt r = getStandardReceipt();
        ItemRow row = getStandardItemRow();
        assertThrows(IllegalArgumentException.class, () -> r.addItem(row.getItem(), 0));
    }

    @Test
    public void receiptReturnsPnr() {
        Customer c = getStandardCustomer();

        assertEquals(200001010001l, c.getPnr());
    }

    @Test
    public void receiptTotalReturnZero() {
        Receipt r = getStandardReceipt();
        assertEquals(new Money(0, Currency.SEK).getAmount(), r.getTotal());
    }

    @Test
    public void receiptAddOnePayment() {
        Receipt r = getStandardReceipt();
        Payment p = getStandardPayment();

        r.addPayment(p);
        assertTrue(r.getPayments().contains(p));
    }

    @Test
    public void receiptAddMultiplePayment() {
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
    public void addItemNullThrows() {
        Receipt r = getStandardReceipt();
        Item item = null;
        double quantity = 1;

        assertThrows(IllegalArgumentException.class, () -> r.addItem(item, quantity));
    }

    @Test
    public void addItemMultipleTimesIncreasesQuantity() {
        Receipt r = getStandardReceipt();
        Item item = getStandardItem();
        double quantity = 1;

        r.addItem(item, quantity);
        r.addItem(item, quantity);
        r.addItem(item, quantity);

        assertEquals(3d, r.getItemRow(item).getQuantity());
    }


    @Test
    public void addItemMultipleTimesToZeroRemovesRow() {
        Receipt r = getStandardReceipt();
        Item item = getStandardItem();

        r.addItem(item, 1);
        r.addItem(item, 2);
        r.addItem(item, -3);

        assertNull(r.getItemRow(item));
    }

    @Test
    public void isNotPaidNoPaymentAdded() {
        Receipt r = getStandardReceipt();
        assertFalse(r.isPaid());
    }

    @Test
    public void isPaidPaymentAdded() {
        Receipt r = getStandardReceipt();
        r.addPayment(getStandardPayment());

        assertTrue(r.isPaid());
    }

    @Test
    public void getBackOneSek() {
        Receipt r = getStandardReceipt();
        Payment p = getStandardPayment();

        r.addPayment(p);

        assertTrue(r.getChange().compareTo(new BigDecimal(-1.000)) == 0);
    }

    @Test
    public void getBackOneThousandSek() {
        Receipt r = getStandardReceipt();
        Payment p = new Payment(new Money(1000, Currency.SEK), PaymentType.CASH);

        r.addPayment(p);

        assertTrue(r.getChange().compareTo(new BigDecimal(-1000.000)) == 0);
    }

    @Test
    public void getChangeMultiplePayments() {
        Receipt r = getStandardReceipt();
        Payment p1 = getStandardPayment();
        Payment p2 = getStandardPayment();
        Payment p3 = getStandardPayment();

        r.addPayment(p1);
        r.addPayment(p2);
        r.addPayment(p3);

        assertTrue(r.getChange().compareTo(new BigDecimal(-3.000)) == 0);
    }

    @Test
    public void getPaymentsList() {
        Receipt r = getStandardReceipt();
        Payment p1 = getStandardPayment();
        Payment p2 = getStandardPayment();
        Payment p3 = getStandardPayment();

        r.addPayment(p1);
        r.addPayment(p2);
        r.addPayment(p3);

        List<Payment> payments = new ArrayList<>();
        payments.add(p1);
        payments.add(p2);
        payments.add(p3);

        assertEquals(payments, r.getPayments());
    }

    @Test
    public void payForAddedItemWithChange() {
        Receipt r = getStandardReceipt();
        Item item = getStandardItem();
        double quantity = 1;

        r.addItem(item, quantity);
        Payment p = getStandardPayment();
        r.addPayment(p);
        r.addPayment(p);

        assertTrue(r.getChange().compareTo(new BigDecimal(0)) < 0);
    }

    @Test
    public void payForAddedItemNoChange() {
        Receipt r = getStandardReceipt();
        Item item = getStandardItem();
        double quantity = 1;

        r.addItem(item, quantity);
        Payment p = new Payment(new Money(1.120, Currency.SEK), PaymentType.CASH);
        r.addPayment(p);

        assertTrue(r.getChange().compareTo(new BigDecimal(0)) == 0);
    }

    @Test
    public void testGetTotalWithoutTaxesAndNoChange() {
        Receipt r = getStandardReceipt();
        Item item = getStandardItem();
        double quantity = 1;
        r.addItem(item, quantity);

        assertTrue(r.getTotalWithoutTaxes().compareTo(new BigDecimal(1.000)) == 0);
    }

    @Test
    public void addPaymentAndThenAddItemNoChange() {
        Receipt r = getStandardReceipt();
        Payment p = getStandardPayment();
        r.addPayment(p);

        Item item = getStandardItem();
        double quantity = 1;
        r.addItem(item, quantity);

        assertTrue(r.getTotalWithoutTaxes().compareTo(new BigDecimal(1.000)) == 0);
    }

    @Test
    public void toStringNotNull() {
        Receipt r = getStandardReceipt();
        assertNotNull(r.toString());
    }

    @Test
    public void toStringWithPaymentsAndItems() {
        Receipt r = getStandardReceipt();
        Payment p1 = getStandardPayment();
        Payment p2 = getStandardPayment();
        Payment p3 = getStandardPayment();

        Item item = getStandardItem();

        r.addItem(item, 1);
        r.addItem(item, 1);
        r.addItem(item, 1);

        r.addPayment(p1);
        r.addPayment(p2);
        r.addPayment(p3);

        assertNotNull(r.toString());
    }
}
