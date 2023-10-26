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