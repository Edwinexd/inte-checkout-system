package com.agie;

public class ItemCategory {
    private final String name;
    private final VATRate vatRate;
    private final AgeLimit ageLimit;

    public ItemCategory (String name, VATRate vatRate, AgeLimit ageLimit) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        if (vatRate == null) {
            throw new IllegalArgumentException("VAT rate cannot be null");
        }
        this.name = name;
        this.vatRate = vatRate;
        this.ageLimit = ageLimit;
    }

    public String getName () {
        return name;
    }

    public VATRate getVatRate () {
        return vatRate;
    }

    /**
     * Returns the age limit for this item category.
     * May be null if there is no age limit.
     */
    public AgeLimit getAgeLimit () {
        return ageLimit;
    }
}
