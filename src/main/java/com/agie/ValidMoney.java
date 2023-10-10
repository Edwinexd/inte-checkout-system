package com.agie;

interface Currency {
    // needs exchange rates 2
    public String getSymbol();
    public int getExchangeRate(Currency other);
}

interface Money {
    public int getAmount();
    public Currency getCurrency();
    public Money add(Money money);
    public Money subtract(Money money);
    public Money multiply(int multiplier);
    public Money divide(int divisor);
    public boolean equals(Object object);
    public Money valueIn(Currency currency);
}