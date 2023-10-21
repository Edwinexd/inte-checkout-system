package com.agie;

public class Item {
    private final String name;
    private final Money unitPrice;
    private final ItemCategory itemCategory;
    private final Deposit deposit;
    private final Supplier supplier;
    private final EAN ean;
    private final boolean weightBased;

    public Item(String name,
            Money unitPrice,
            ItemCategory itemCategory,
            Deposit deposit,
            Supplier supplier,
            EAN ean,
            boolean weightBased) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        if (unitPrice == null) {
            throw new IllegalArgumentException("Unit price cannot be null");
        }
        if (itemCategory == null) {
            throw new IllegalArgumentException("Item category cannot be null");
        }
        if (supplier == null) {
            throw new IllegalArgumentException("Supplier cannot be null");
        }
        this.name = name;
        this.unitPrice = unitPrice;
        this.itemCategory = itemCategory;
        this.deposit = deposit;
        this.supplier = supplier;
        this.ean = ean;
        this.weightBased = weightBased;
    }

    public String getName() {
        return name;
    }

    public Money getUnitPrice() {
        return unitPrice;
    }

    public ItemCategory getItemCategory() {
        return itemCategory;
    }

    public Deposit getDeposit() {
        return deposit;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public EAN getEAN() {
        return ean;
    }

    /**
     * Returns true if the unit price is based on weight in kilograms.
     */
    public boolean isWeightBased() {
        return weightBased;
    }

    @Override
    public int hashCode() {
        return name.hashCode() *
                unitPrice.hashCode() *
                itemCategory.hashCode() *
                supplier.hashCode() *
                Boolean.hashCode(weightBased);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Item) {
            Item item = (Item) object;
            return name.equals(item.name) &&
                    unitPrice.equals(item.unitPrice) &&
                    itemCategory.equals(item.itemCategory) &&
                    supplier.equals(item.supplier) &&
                    weightBased == item.weightBased;
        }
        return false;
    }

    @Override
    public String toString() {
        if (weightBased) {
            return String.format("%s - %s kr / kg", name, unitPrice);
        } else {
            return String.format("%s - %s kr", name, unitPrice);
        }
    }

}
