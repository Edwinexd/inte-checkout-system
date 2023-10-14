package com.agie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.regex.Pattern;

interface ReceiptInterface {
    public int getId(); // sequence
    // säger inget om kvanitet
    public Collection<ItemRow> getItemRows();
    public int getTotal();
    public int getTotalExVat();
    public void addRow(ItemRow itemToAddtoRow);
    public void removeItemRow(ItemRow itemRow); // diskuterbart

    public Date getDate();

    public Customer getCustomer(); // null-able

    public ArrayList<Payment> getPayments();

    void addPayment(Payment payment);

    public boolean isPaid();

    public Money getChange(); // throws IllegalSate if not paid
    
    // ?
    public Discount[] getDiscounts();

}
