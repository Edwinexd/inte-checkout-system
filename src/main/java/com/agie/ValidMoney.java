package com.agie;

interface ValidCurrency {
    // needs exchange rates 2
    public String getSymbol();
    public int getExchangeRate(ValidCurrency other);
}

public interface ValidMoney {
    public int getAmount();
    public Currency getCurrency();
    public ValidMoney add(ValidMoney money);
    public ValidMoney subtract(ValidMoney money);
    public ValidMoney multiply(int multiplier);
    public ValidMoney divide(int divisor);
    public boolean equals(Object object);
    public ValidMoney valueIn(Currency currency);
}