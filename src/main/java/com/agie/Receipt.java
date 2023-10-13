package com.agie;

import sun.jvm.hotspot.utilities.AssertionFailure;

import java.util.ArrayList;
import java.util.Date;

public class Receipt implements ReceiptInterface {

    private ArrayList<ItemRow> itemRowHolder;
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

        itemRowHolder = new ArrayList<>();
        totalWithoutTaxes = new Money();
        taxesOnly = new Money();
        isPaid = false;
        paymentHolder = new ArrayList<>();
        receiptDate = new Date();
        paymentHolder = new ArrayList<>();

        this.customer = customer;
    }

    @Override
    public int getId() {
        return this.Id;
    }

    @Override
    public ArrayList<ItemRow> getItemRows() {
        return this.itemRowHolder;
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
    public void addRow(ItemRow itemToAdd) {
        if(itemToAdd.equals(null)){
            throw new IllegalArgumentException();
        }
        this.itemRowHolder.add(itemToAdd);
    }

    @Override
    public void removeItemRow(ItemRow itemToRemove) {
        this.itemRowHolder.remove(itemToRemove);
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
