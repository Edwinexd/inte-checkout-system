package com.agie;

import java.util.Date;

interface Receipt {
    public int getId(); // sequence
    // säger inget om kvanitet
    public ItemRow[] getItemRows();
    public Money getTotal();
    public Money getTotalExVat();
    public Money getTotalIncVat();
    // ta emot item eller itemRow?
    public void addRow(ItemRow itemRow);
    public void removeItemRow(ItemRow itemRow); // diskuterbart

    public Date getDate();

    public Customer getCustomer(); // null-able

    public Payment[] getPayments();

    public boolean isPaid();

    public Money getChange(); // throws IllegalSate if not paid
    
    // ?
    public Discount[] getDiscounts();

}