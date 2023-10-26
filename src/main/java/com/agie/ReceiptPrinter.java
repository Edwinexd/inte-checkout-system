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

import java.util.Date;

public interface ReceiptPrinter {

    //Print the receipt that is set as currentReceipt in the register.
	//// printReceipt
    public boolean printActiveReceipt() throws IllegalStateException;

    //Print the supplied receipt object.
    public boolean printReceipt(Receipt receipt) throws IllegalStateException;

    //Print the object with the supplied id.
    public boolean printReceipt(int id) throws IllegalStateException;

    //Print all receipts created today.
    public boolean printDailyReceipts() throws IllegalStateException;

    //Print all receipts created today by any specific employee.
    public boolean printDailyReceiptsByEmployee(int employeeId) throws IllegalStateException;

    //Print all receipts created on the supplied date.
    public boolean printDailyReceipts(Date date) throws IllegalStateException;

    //Print all receipts created on the supplied date by the specified employee.
    public boolean printDailyReceiptsByEmployee(Date date) throws IllegalStateException;

    //Print all receipts created during the specified timeframe.
    public boolean printReceiptsDuring(Date dateFrom, Date dateTo) throws IllegalStateException;
    
    public int getPaperLeft();
    
    public boolean abortCurrentPrint();
    
    public void reprintLast();
}


