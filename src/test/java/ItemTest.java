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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.agie.EAN;
import com.agie.Money;
import com.agie.Supplier;
import com.agie.VATRate;
import com.agie.Currency;
import com.agie.Deposit;
import com.agie.Item;
import com.agie.ItemCategory;


public class ItemTest {
    private static final String VALID_NAME = "Test";
    private static final Money VALID_UNIT_PRICE = new Money(1, Currency.SEK);
    private static final ItemCategory VALID_ITEM_CATEGORY = new ItemCategory("Fruit", VATRate.VAT_12, null);
    private static final Deposit VALID_DEPOSIT = Deposit.ONE;
    private static final Supplier VALID_SUPPLIER = new Supplier("Test");
    private static final EAN VALID_EAN = new EAN(1234567890128L);
    private static final boolean WEIGHT_BASED = true;

    @Test
    public void constructorTest() {
        new Item(VALID_NAME, VALID_UNIT_PRICE, VALID_ITEM_CATEGORY, VALID_DEPOSIT, VALID_SUPPLIER, VALID_EAN, WEIGHT_BASED);
    }

    // I've chosen to test all invalid nulls under the same test as they would be identical for the most part.
    @Test
    public void constructorNullThrows () {
        assertThrows(IllegalArgumentException.class, () -> {
            new Item(null, VALID_UNIT_PRICE, VALID_ITEM_CATEGORY, VALID_DEPOSIT, VALID_SUPPLIER, VALID_EAN, WEIGHT_BASED);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Item(VALID_NAME, null, VALID_ITEM_CATEGORY, VALID_DEPOSIT, VALID_SUPPLIER, VALID_EAN, WEIGHT_BASED);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Item(VALID_NAME, VALID_UNIT_PRICE, null, VALID_DEPOSIT, VALID_SUPPLIER, VALID_EAN, WEIGHT_BASED);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Item(VALID_NAME, VALID_UNIT_PRICE, VALID_ITEM_CATEGORY, VALID_DEPOSIT, null, VALID_EAN, WEIGHT_BASED);
        });
    }

    @Test
    public void constructorNullDepositOk () {
        new Item(VALID_NAME, VALID_UNIT_PRICE, VALID_ITEM_CATEGORY, null, VALID_SUPPLIER, VALID_EAN, WEIGHT_BASED);
    }

    @Test
    public void constructorNullEANOk () {
        new Item(VALID_NAME, VALID_UNIT_PRICE, VALID_ITEM_CATEGORY, VALID_DEPOSIT, VALID_SUPPLIER, null, WEIGHT_BASED);
    }

    @Test
    public void equalsTest() {
        Item item1 = new Item(VALID_NAME, VALID_UNIT_PRICE, VALID_ITEM_CATEGORY, VALID_DEPOSIT, VALID_SUPPLIER, VALID_EAN, WEIGHT_BASED);
        Item item2 = new Item(VALID_NAME, VALID_UNIT_PRICE, VALID_ITEM_CATEGORY, VALID_DEPOSIT, VALID_SUPPLIER, VALID_EAN, WEIGHT_BASED);
        assertEquals(item1, item2);
    }

    @Test
    public void toStringWeightBasedTest() {
        Item item = new Item(VALID_NAME, VALID_UNIT_PRICE, VALID_ITEM_CATEGORY, VALID_DEPOSIT, VALID_SUPPLIER, VALID_EAN, true);
        assertEquals(String.format("%s - %s kr / kg", VALID_NAME, VALID_UNIT_PRICE), item.toString());
    }

    @Test
    public void toStringNotWeightBasedTest() {
        Item item = new Item(VALID_NAME, VALID_UNIT_PRICE, VALID_ITEM_CATEGORY, VALID_DEPOSIT, VALID_SUPPLIER, VALID_EAN, false);
        assertEquals(String.format("%s - %s kr", VALID_NAME, VALID_UNIT_PRICE), item.toString());
    }
}
