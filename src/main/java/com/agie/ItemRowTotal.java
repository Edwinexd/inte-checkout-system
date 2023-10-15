package com.agie;

public class ItemRowTotal {
    public final Money total;
    public final Money vat;
    public final Money totalWithVat;

    public ItemRowTotal(Money total, Money vat, Money totalWithVat) {
        this.total = total;
        this.vat = vat;
        this.totalWithVat = totalWithVat;
    }
}