import com.agie.*;
import jdk.vm.ci.code.Register;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ReceiptTest {
    public Receipt getStandardReceipt(){
        return new Receipt(1, getStandardCustomer());
    }
    public Customer getStandardCustomer(){
        return new Customer(0001010001);
    }
    public ItemRow getStandardItemRow(){
        Item apple = new Item("Apple", null, null, null, null, null, true);
        return new ItemRow(apple, 1);
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

        assertDoesNotThrow(() -> receipt.addRow(row));
    }

    @Test
    public void receiptReturnsPnr(){
        Customer c = getStandardCustomer();

        assertEquals(0001010001, c.getPnr());
    }

    @Test
    public void receiptTotalReturnZero(){
        Receipt r = getStandardReceipt();
        assertEquals(0, r.getTotal());
    }

    @Test
    public void receiptAddOnePayment(){ // TODO: r.GetPayments returns an arraylist of payments, not a single payment?
        Receipt r = getStandardReceipt();
        Payment p = new Payment();

        r.addPayment(p);
        assertEquals(p, r.getPayments());
    }

    @Test
    public void receiptAddMultiplePayment(){
        Receipt r = getStandardReceipt();
        Payment p1 = new Payment();
        Payment p2 = new Payment();
        Payment p3 = new Payment();

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
    public void receiptAddNullItemRow(){
        Receipt r = getStandardReceipt();
        ItemRow i = null;

        assertThrows(IllegalArgumentException.class, () -> r.addRow(i));
    }

    @Test
    public void receiptRemoveItemRow(){
        Receipt r = getStandardReceipt();
        ItemRow i = getStandardItemRow();

        r.addRow(i);
        r.removeItemRow(i);
        assertEquals(0, r.getItemRows().size());
    }

    @Test
    public void receiptGetItemRows(){
        Receipt r = getStandardReceipt();
        ItemRow i = getStandardItemRow();

        r.addRow(i);
        assertEquals(i, r.getItemRows());
    }

    @Test
    public void receiptGetMultipleItemRows(){
        Receipt r = getStandardReceipt();
        ItemRow i1 = getStandardItemRow(); // ItemRow of the same item
        ItemRow i2 = getStandardItemRow();
        ItemRow i3 = getStandardItemRow();

        ArrayList<ItemRow> listOfItemRows = new ArrayList<>();

        listOfItemRows.add(i1);
        listOfItemRows.add(i2);
        listOfItemRows.add(i3);

        r.addRow(i1);
        r.addRow(i2);
        r.addRow(i3);
        assertEquals(listOfItemRows, r.getItemRows());
    }

    @Test
    public void receiptIsPaid(){ // Its a hard knock life for us
        Receipt r = getStandardReceipt();
        Payment p = new Payment();
        r.addPayment(p);

        assertTrue(r.isPaid());
    }

    @Test
    public void receiptIsNotPaid(){
        Receipt r = getStandardReceipt();

        assertFalse(r.isPaid());
    }

    @Test
    public void receiptGetChange(){ // TODO: FIX
        Receipt r = getStandardReceipt();
        Payment p = new Payment();

        r.addPayment(p);
        assertEquals(0, r.getChange());
    }

    @Test
    public void receiptGetChangeMultiplePayments(){
        Receipt r = getStandardReceipt();
        Payment p1 = new Payment();
        Payment p2 = new Payment();
        Payment p3 = new Payment();

        r.addPayment(p1);
        r.addPayment(p2);
        r.addPayment(p3);
        assertEquals(0, r.getChange()); // TODO: FIX
    }
}
