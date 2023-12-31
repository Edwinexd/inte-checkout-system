/*
Checkout System - A checkout system with focus on testing - group assignment for the INTE course at Stockholm University Autumn 2023
Copyright (C) 2023 Gusten Berghäll, Ida Laaksonen, Adrian Martvall, Edwin Sundberg 

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/
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

    public Receipt getReceiptWithoutCustomer() {
        return new Receipt(1, null);
    }
    
    public Receipt getReceiptWithYoungCustomer() {
        return new Receipt(1, new Customer(201001010001l));
    }

    
    public Item getStandardItem() {
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

    public ItemRow getStandardItemRow() {
        return new ItemRow(getStandardItem(), 1);
    }

    // Tests start here

    @Test
    public void notNull() {
        assertNotNull(getStandardReceipt());
    }

    @Test
    public void constructorDoesNotThrow() {
        assertDoesNotThrow(() -> getStandardReceipt());
    }

    @Test
    public void getId() {
        Receipt r = getStandardReceipt();
        assertEquals(1, r.getId());
    }

    @Test
    public void dateNotNull() {
        Receipt r = getStandardReceipt();
        assertNotNull(r.getDate());
    }

    @Test
    public void correctDateDatatype() {
        Receipt r = getStandardReceipt();

        assertTrue(r.getDate() instanceof Date);
    }

    // Date starts counting years from 1900
    @Test
    public void correctYear() {
        Receipt r = getStandardReceipt();
        assertEquals(Calendar.getInstance().get(Calendar.YEAR), r.getDate().getYear() + 1900);
    }

    @Test
    public void correctMonth() {
        Receipt r = getStandardReceipt();
        assertEquals(Calendar.getInstance().get(Calendar.MONTH), r.getDate().getMonth());
    }

    @Test
    public void correctDay() {
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
    public void idCantBeLessThanOne() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Receipt(-1, getStandardCustomer());
        });
    }

    @Test
    public void addItemDoesNotThrow() {
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
    public void returnsPnr() {
        Customer c = getStandardCustomer();

        assertEquals(200001010001l, c.getPnr());
    }

    @Test
    public void totalReturnZero() {
        Receipt r = getStandardReceipt();
        assertEquals(new Money(0, Currency.SEK).getAmount(), r.getTotal());
    }

    @Test
    public void addOnePayment() {
        Receipt r = getStandardReceipt();
        Payment p = getStandardPayment();

        r.addPayment(p);
        assertTrue(r.getPayments().contains(p));
    }

    @Test
    public void addMultiplePayment() {
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
    public void toStringNotNullWithPaymentsAndItems() {
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
