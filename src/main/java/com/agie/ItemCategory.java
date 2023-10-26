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
