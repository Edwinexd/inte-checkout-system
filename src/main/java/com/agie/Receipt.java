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

    public Receipt(final int id, final Customer customer) {
        if (id < 0) {
            throw new IllegalArgumentException("ID can't be negative.");
        }
        this.id = id;

        this.customer = customer;

        totalWithoutTaxes = new Money(0, Currency.SEK);
        taxesOnly = new Money(0, Currency.SEK);

        receiptDate = new Date(); // timestamp
        isPaid = false;
    }

    public int getId() {
        return this.id;
    }

    public ItemRow getItemRow(Item item) {
        return itemRowHolder.get(item);
    }

    public BigDecimal getTotal() {
        return this.totalWithoutTaxes.getAmount().add(this.taxesOnly.getAmount());
    }

    public BigDecimal getTotalWithoutTaxes() {
        return this.totalWithoutTaxes.getAmount();
    }

    public Date getDate() {
        return receiptDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Collection<Payment> getPayments() {
        return paymentHolder;
    }

    private HashMap<Item, ItemRow> getItemRowHolder() {
        return itemRowHolder;
    }

    public BigDecimal getChange() {
        final BigDecimal total = this.getTotal();
        Money change = new Money(total, Currency.SEK);

        for (int i = 0; i < getPayments().size(); i++) {
            change = change.subtract(paymentHolder.get(i).getMoney());
        }
        return change.getAmount();
    }

    /*
     * Adds a specified quantity of an item to the receipt.
     * If the new total quantity of the item becomes zero
     * the item is removed from the receipt.
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
        updateTotal();
    }

    private void updateTotal() {
        for (ItemRow itemRow : itemRowHolder.values()) {
            totalWithoutTaxes = totalWithoutTaxes.add(itemRow.getItem().getUnitPrice());
            taxesOnly = taxesOnly.add(itemRow.getItem().getUnitPrice().multiply(itemRow.getItem().getItemCategory().getVatRate().getRate()));
        }
    }

    public void addPayment(Payment payment) {
        paymentHolder.add(payment);
        checkIfPaid();
    }

    private void checkIfPaid() {
        final BigDecimal total = this.getTotal();
        BigDecimal amountPaid = new BigDecimal(0);
        for (Payment payment : paymentHolder) {
            amountPaid = amountPaid.add(payment.getMoney().getAmount());

            //If the customer has paid the exact amount or more, the receipt is paid
            if (amountPaid.compareTo(total) >= 0) {
                this.isPaid = true;
                return;
            }
        }
        this.isPaid = false;
    }

    public boolean isPaid() {
        return this.isPaid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Receipt ID: ");
        sb.append(getId());
        sb.append("\n");

        sb.append("Customer: ");
        sb.append(getCustomer().getPnr());
        sb.append("\n");

        sb.append("Date: ");
        sb.append(getDate());
        sb.append("\n");

        sb.append("Items: ");
        sb.append("\n");
        for (Item item : getItemRowHolder().keySet()) {
            sb.append("Item: ");
            sb.append(item.getName());
            sb.append(" Quantity: ");
            sb.append(getItemRowHolder().get(item).getQuantity());
            sb.append("\n");
        }

        sb.append("Total: ");
        sb.append(getTotal());
        sb.append("\n");

        sb.append("Payments: ");
        sb.append("\n");
        for (Payment payment : getPayments()) {
            sb.append("Payment type: ");
            sb.append(payment.getPaymentType());
            sb.append(" Amount: ");
            sb.append(payment.getMoney().getAmount());
            sb.append("\n");
        }

        sb.append("Change: ");
        sb.append(getChange());
        sb.append("\n");

        sb.append("Is paid: ");
        sb.append(isPaid());

        return sb.toString();
    }
}
