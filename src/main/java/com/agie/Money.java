package com.agie;

import java.math.BigDecimal;
import java.util.HashMap;

public class Money {
	
	private HashMap<Currency, BigDecimal> exchangeRatesToSek = new HashMap<>();
	private BigDecimal amountInSek;
	
	public Money(BigDecimal amount, Currency currency) {
		exchangeRatesToSek.put(Currency.SEK, new BigDecimal(1));
		this.amountInSek = amount.multiply(exchangeRatesToSek.get(currency));
	}
	
	public Money(double amount, Currency currency) {
		exchangeRatesToSek.put(Currency.SEK, new BigDecimal(1));
		String amountString = String.valueOf(amount);
		BigDecimal amountBigDecimal = new BigDecimal(amountString);
		this.amountInSek = amountBigDecimal.multiply(exchangeRatesToSek.get(currency));
	}
	
	public Money(int amount, Currency currency) {
		exchangeRatesToSek.put(Currency.SEK, new BigDecimal(1));
		BigDecimal amountBigDecimal = new BigDecimal(amount);
		this.amountInSek = amountBigDecimal.multiply(exchangeRatesToSek.get(currency));
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
	
	public Money divide(Money money) {
		return new Money(this.amountInSek.divide(money.amountInSek), Currency.SEK);
	}

}
