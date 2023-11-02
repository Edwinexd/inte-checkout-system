/*
Checkout System - A checkout system with focus on testing - group assignment for the INTE course at Stockholm University Autumn 2023
Copyright (C) 2023 Gusten Berghäll, Ida Laaksonen, Adrian Martvall, Edwin Sundberg 

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/
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
