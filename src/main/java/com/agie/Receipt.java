package com.agie;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

public class Receipt {

    private HashMap<Item, ItemRow> itemRowHolder = new HashMap<>();
    private ArrayList<Payment> paymentHolder = new ArrayList<>();
    private final Date receiptDate;
    private final Customer customer;
    private final int id;
    private Money totalWithoutTaxes;
    private Money taxesOnly;
    private boolean isPaid;

    public Receipt(int id, Customer customer){
        if (id < 0) {
            throw new IllegalArgumentException("ID can't be negative.");
        }
        this.id = id;

        /*
        if(customer == null){
            throw new IllegalArgumentException("Customer can't be null.");
        }
         */

        this.customer = customer;

        totalWithoutTaxes = new Money(0, Currency.SEK); //TODO: Could there be EUR? USD? GBP? etc. Worth looking into.
        taxesOnly = new Money(0 , Currency.SEK);

        receiptDate = new Date(); // timestamp
        isPaid = false;
    }

    public int getId() {
        return this.id;
    }

    private HashMap<Item, ItemRow> getItemRowHolder() {
        return itemRowHolder;
    }

    public ItemRow getItemRow(Item item) {
        return itemRowHolder.get(item);
    }

    /*
     * Adds a specified quantity of an item to the receipt.
     * If the new total quantity of the item becomes zero, the item is removed from the receipt.
     */
    public void addItem(Item item, double quantity) {

        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        if (quantity == 0) {
            throw new IllegalArgumentException("Quantity cannot be zero");
        }

        if (item.getItemCategory().getAgeLimit() != null && (customer == null || customer.getAge() < item.getItemCategory().getAgeLimit().getAgeLimit())) {
            throw new IllegalArgumentException("Customer is not old enough to purchase this item");
        }

        ItemRow itemRow = itemRowHolder.get(item);
        if (itemRow == null) {
            itemRow = new ItemRow(item, quantity);
        } else {
            try {
                itemRow = itemRow.addQuantity(quantity);
            } catch (IllegalArgumentException e) {
                itemRowHolder.remove(item);
                return;
            }
        }
        itemRowHolder.put(item, itemRow);
    }

    public BigDecimal getTotal() {
        return this.totalWithoutTaxes.getAmount().add(this.taxesOnly.getAmount());
    }

    // TODO: Arbeta med Money abstraktionen ist
    public BigDecimal getTotalWithoutTaxes() {
        return this.totalWithoutTaxes.getAmount();
    }

    public Date getDate() { // Kanske return receiptDate.getTime(); ?
        return receiptDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Collection<Payment> getPayments() {
        return paymentHolder;
    }

    public void addPayment(Payment payment) {

        if(payment == null){
            throw new IllegalArgumentException("Payment cannot be null");
        }

        /*
        Lägg till detta i Payment klassen.
        Borde inte kollas i Receipt

        if(payment.getMoney().getAmount().compareTo(BigDecimal.ZERO) == 0){
            throw new IllegalArgumentException("Payment cannot be zero");
        }

        if(payment.getMoney().getAmount().compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Payment cannot be negative");
        }
         */

        paymentHolder.add(payment);

        checkIfPaid();
    }

    // TODO: Work with Money abstraction layer instead of BigDecimal
    private void checkIfPaid() {
        BigDecimal total = this.getTotal();
        BigDecimal amountPaid = new BigDecimal(0);
        for (int i = 0; i < paymentHolder.size(); i++) { // There probably is an easier way to do this
            amountPaid = amountPaid.add(paymentHolder.get(i).getMoney().getAmount());

            //If the customer has paid the exact amount or more, the receipt is paid
            if(amountPaid.compareTo(total) >= 0){
                // TODO: Borde inte vara ett fält på klassen
                this.isPaid = true;
            }
        }
    }

    public boolean isPaid() {
        return this.isPaid;
    }

    public Money getChange() {
        BigDecimal total = this.getTotal();
        Money change = new Money(total, Currency.SEK);

        for (int i = 0; i < getPayments().size(); i++) {
            change = change.subtract(paymentHolder.get(i).getMoney());
        }

        return change;
    }

    /*
    public Discount[] getDiscounts() {
        return new Discount[0];
    }
     */
}
