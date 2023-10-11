package com.agie;

// in practice item type.
interface Item {
    public String getName();
    public Money getUnitPrice();
    public boolean equals(Object object);
    public ItemCategory getItemCategory();
    public Deposit getDeposit(); // pant
    // vill man ha den här?
    public boolean isAgeLimitSatisfied(int age);
    public boolean isAgeLimitSatisfied(Customer customer);

    public Supplier getSupplier();

    public EAN getEAN();

    public boolean isWeightBased(); // kg
}