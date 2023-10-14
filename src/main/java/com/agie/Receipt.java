package com.agie;

import sun.jvm.hotspot.utilities.AssertionFailure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

public class Receipt implements ReceiptInterface {

    private HashMap<Item, ItemRow> itemRows = new HashMap<>();
    private int Id;
    private Money totalWithoutTaxes;
    private Money taxesOnly;
    private boolean isPaid;
    private ArrayList<Payment> paymentHolder;
    private Date receiptDate;
    private Customer customer;

    public Receipt(int Id, Customer customer){
        if (Id <= -1) {
            throw new IllegalArgumentException("ID can't be less than one.");
        }
        this.Id = Id;

        totalWithoutTaxes = new Money();
        taxesOnly = new Money();
        isPaid = false;
        paymentHolder = new ArrayList<>();
        receiptDate = new Date(); // Dag, timme, minut, sekund

        this.customer = customer;
    }

    @Override
    public int getId() {
        return this.Id;
    }

    public Collection<ItemRow> getItemRows() {
        return itemRows.values();
    }

    public ItemRow getItemRow(Item item) {
        return itemRows.get(item);
    }

    public void addItem(Item item, double quantity) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        if (quantity == 0) {
            throw new IllegalArgumentException("Quantity cannot be zero");
        }

        ItemRow itemRow = itemRows.get(item);
        if (itemRow == null) {
            itemRow = new ItemRow(item, quantity);
        } else {
            itemRow = itemRow.addQuantity(quantity);
        }
        if (itemRow.getQuantity() == 0) {
            itemRows.remove(item);
        } else {
            itemRows.put(item, itemRow);
        }
    }

    @Override
    public int getTotal() {
        return this.totalWithoutTaxes.getAmount() + this.taxesOnly.getAmount();
    }

    @Override
    public int getTotalExVat() {
        return this.totalWithoutTaxes.getAmount();
    }


    @Override
    public Date getDate() { // Kanske return receiptDate.getTime(); ?
        return receiptDate;
    }

    @Override
    public Customer getCustomer() {
        return customer;
    }

    @Override
    public ArrayList<Payment> getPayments() {
        return paymentHolder;
    }

    @Override
    public void addPayment(Payment payment) {
        paymentHolder.add(payment);
    }

    @Override
    public boolean isPaid() {
        return this.isPaid;
    }

    @Override
    public int getChange() { // Gör om till money när money är gjord.

        int change = getTotal();

        for (int i = 0; i < getPayments().size(); i++) {
            change = change - getPayments().get(i).getAmount(); // Någonting liknande
        }

        return change;
    }

    @Override
    public Discount[] getDiscounts() {
        return new Discount[0];
    }
}
