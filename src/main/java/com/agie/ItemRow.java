package com.agie;

public class ItemRow {
    private final Item item;
    private final double quantity; // negative for returns, non-integer for weight-based items

    public ItemRow(Item item, double quantity) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        if (quantity == 0) {
            throw new IllegalArgumentException("Quantity cannot be zero");
        }
        this.item = item;
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public double getQuantity() {
        return quantity;
    }

    public ItemRowTotal getTotal() {
        Money total = item.getUnitPrice().multiply(quantity);
        Money vat = total.multiply(item.getItemCategory().getVatRate().getRate());
        Money totalWithVat = total.add(vat);
        return new ItemRowTotal(total, vat, totalWithVat);
    }

    public ItemRow addQuantity(double quantity) {
        double newQuantity = this.quantity + quantity;
        if (newQuantity == 0) {
            throw new IllegalArgumentException("Quantity cannot be zero");
        }
        return new ItemRow(item, newQuantity);
    }

    public ItemRow subtractQuantity(double quantity) {
        return addQuantity(-quantity);
    }

    @Override
    public String toString() {
        return String.format("%.3f - %s", quantity, item.toString());
    }
}
