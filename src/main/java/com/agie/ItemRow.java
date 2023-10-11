package com.agie;

interface ItemRow {
    public Item getItem();
    public int getQuantity(); // negative for returns
    public Money getTotal();
}

public class ItemRow {
    private final Item item;
    private final double quantity;

    public ItemRowImpl(Item item, double quantity) {
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

    public int getQuantity() {
        return quantity;
    }

    public Money getTotal() {
        return item.getUnitPrice().multiply(quantity);
    }
}
