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


