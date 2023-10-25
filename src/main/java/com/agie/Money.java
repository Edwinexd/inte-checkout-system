package com.agie;

import java.math.BigDecimal;
import java.util.HashMap;


public class Money {
	private static final int SCALE = 3;

	private HashMap<Currency, BigDecimal> exchangeRatesToSek = new HashMap<>();
	private BigDecimal amountInSek;
	
	public Money(BigDecimal amount, Currency currency) {
		if (amount == null) { 
			throw new IllegalArgumentException("Amount cannot be null");
		}
		if (currency == null) {
			throw new IllegalArgumentException("Currency cannot be null");
		}
		amount = amount.setScale(SCALE, java.math.RoundingMode.HALF_UP);
		exchangeRatesToSek.put(Currency.SEK, new BigDecimal(1));
		exchangeRatesToSek.put(Currency.EUR, new BigDecimal(10));
		this.amountInSek = amount.multiply(exchangeRatesToSek.get(currency));
	}
	
	public Money(double amount, Currency currency) {
		this(new BigDecimal(amount), currency);
	}
	
	public Money(int amount, Currency currency) {
		this(new BigDecimal(amount), currency);
	}

	public BigDecimal getAmount(Currency currency) {
		BigDecimal exchangeRate = exchangeRatesToSek.get(currency);
		return amountInSek.multiply(exchangeRate);
	}
	
	public BigDecimal getAmount() {
		return amountInSek;
	}
	
	public Money add(Money money) {
		return new Money(this.amountInSek.add(money.amountInSek), Currency.SEK);
	}
	
	public Money subtract(Money money) {
		return new Money(this.amountInSek.subtract(money.amountInSek), Currency.SEK);
	}
	
	public Money multiply(Money money) {
		return new Money(this.amountInSek.multiply(money.amountInSek), Currency.SEK);
	}

	public Money multiply(int multiplier) {
		return multiply(new Money(multiplier, Currency.SEK));
	}

	public Money multiply(double multiplier) {
		return multiply(new Money(multiplier, Currency.SEK));
	}

	public Money multiply(BigDecimal multiplier) {
		return multiply(new Money(multiplier, Currency.SEK));
	}

	public Money divide(Money money) {
		return new Money(this.amountInSek.divide(money.amountInSek, java.math.RoundingMode.HALF_UP), Currency.SEK);
	}

	public Money divide(int divisor) {
		return divide(new Money(divisor, Currency.SEK));
	}

	public Money divide(double divisor) {
		return divide(new Money(divisor, Currency.SEK));
	}

	public Money divide(BigDecimal divisor) {
		return divide(new Money(divisor, Currency.SEK));
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Money) {
			Money money = (Money) obj;
			return this.amountInSek.equals(money.amountInSek);
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format("%.2f", amountInSek);
	}
}
