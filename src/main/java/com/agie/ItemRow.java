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

public class ItemRow {
    private final Item item;
    private final double quantity; // negative for returns, non-integer for weight-based items

    public ItemRow(Item item, double quantity) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        if (quantity == 0) {
            throw new IllegalArgumentException("Quantity cannot be zero");
        }
        this.item = item;
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public double getQuantity() {
        return quantity;
    }

    public ItemRowTotal getTotal() {
        Money total = item.getUnitPrice().multiply(quantity);
        Money vat = total.multiply(item.getItemCategory().getVatRate().getRate());
        Money totalWithVat = total.add(vat);
        return new ItemRowTotal(total, vat, totalWithVat);
    }

    public ItemRow addQuantity(double quantity) {
        double newQuantity = this.quantity + quantity;
        if (newQuantity == 0) {
            throw new IllegalArgumentException("Quantity cannot be zero");
        }
        return new ItemRow(item, newQuantity);
    }

    public ItemRow subtractQuantity(double quantity) {
        return addQuantity(-quantity);
    }

    @Override
    public String toString() {
        return String.format("%.3f - %s", quantity, item.toString());
    }
}
