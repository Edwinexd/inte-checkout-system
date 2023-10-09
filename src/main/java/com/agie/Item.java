package com.agie;

// in practice item type.
interface Item {
    public String getName();
    public Money getPrice(); // vad symboliserar priset?
    public boolean equals(Object object);
    public VatRate getVatRate(); // momssats
    public ItemType getItemType();
    public Deposit getDeposit(); // pant
    public AgeLimit getAgeLimit();
    // vill man ha den här?
    public boolean isAgeLimitSatisfied(int age);
    public boolean isAgeLimitSatisfied(Customer customer);

    public Supplier getSupplier();

    public EAN getEAN();
    public PLU getPLU();

    public boolean isWeightBased(); // kg
}