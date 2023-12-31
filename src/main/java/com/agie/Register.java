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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

public class Register {

	private int registerNumber;
	private HashMap<EAN, Item> itemCollection = new HashMap(); 
	private HashMap<Integer, Receipt> receiptHistory = new HashMap();
	private HashMap<Integer, Receipt> parkedReceipts = new HashMap();
	private HashMap<Integer, Employee> allEmployees = new HashMap();
	private HashMap<String, ItemCategory> itemCategories = new HashMap();
	private HashMap<String, Supplier> suppliers = new HashMap<>();
	private Receipt currentReceipt;
	private Employee loggedInEmployee;
	private Customer customer;
	private int receiptIdCounter;
	private int employeeId;
	private ReceiptPrinter receiptPrinter;
	private Optional<Logger> logger;

	public Register(int registerNr) {
		this(registerNr, null);
	}

	public Register(int registerNr, Logger logger) {
		this.registerNumber = registerNr;
		this.logger = Optional.ofNullable(logger);
	}

	private void log(String message) {
		log(LogLevel.INFO, message);
	}

	private void log(LogLevel logLevel, String message) {
		if (logger.isPresent()) {
			try {
				logger.get().log(LogLevel.INFO, message);
			} catch (LoggingException e) {
				throw new RuntimeException("Error logging message", e);
			}
		}
	}
	

	public Employee getEmployee(int id) {
		return allEmployees.get(id);
	}

	public Receipt getCurrentReceipt() {
		return currentReceipt;
	}

	private String formatStringInput(String theString) {
		theString = theString.trim();
		theString = theString.toLowerCase();
		if (theString.isEmpty()) {
			throw new IllegalArgumentException("String is empty");
		}
		return theString;
	}

	public ItemCategory addItemCategory(String name, VATRate vatRate, AgeLimit ageLimit) {
		name = formatStringInput(name);
		if (itemCategories.get(name) != null) {
			throw new IllegalArgumentException("Itemcategory already created");
		}
		ItemCategory itemCategory = new ItemCategory(name, vatRate, ageLimit);
		itemCategories.put(name, itemCategory);
		return itemCategory;
	}

	
	public void removeItemCategory(String name) {
		name = formatStringInput(name);
		if (itemCategories.get(name) == null) {
			throw new IllegalArgumentException("No item category with that name");
		}
		itemCategories.remove(name);
	}

	
	public Supplier addSupplier(String name) {
		name = formatStringInput(name);
		if (suppliers.get(name) != null) {
			throw new IllegalArgumentException("Supplier already created");
		}
		Supplier supplier = new Supplier(name);
		suppliers.put(name, supplier);
		return supplier;
	}

	public void removeSupplier(String name) {
		name = formatStringInput(name);
		if (suppliers.get(name) == null) {
			throw new IllegalArgumentException("No supplier with that name");
		}
		suppliers.remove(name);
	}
	

	// TODO: se över om meden ska ta in itemcategory resp supplier objekt istf strängar
	public Item addItem(String name, int costInSek, String itemCategory, Deposit deposit, String supplier,
			AgeLimit ageLimit, long eanNumber, boolean weightBased) {

		name = formatStringInput(name);
		EAN ean = new EAN(eanNumber);
		if (itemCollection.get(ean) != null) {
			throw new IllegalArgumentException("Item with that name already created");
		}
		if (itemCategories.get(itemCategory) == null) {
			throw new IllegalArgumentException("Itemcategory does not exist");
		}
		if (suppliers.get(supplier) == null) {
			throw new IllegalArgumentException("Supplier does not exist");
		}
		Item item = new Item(name, new Money(costInSek, Currency.SEK), itemCategories.get(itemCategory), deposit,
				suppliers.get(supplier), ean, weightBased);
		itemCollection.put(ean, item);
		return item;
	}

	public void removeItem(EAN ean) {
		if (itemCollection.get(ean) == null) {
			throw new IllegalArgumentException("No Item with that name");
		}
		itemCollection.remove(ean);
	}

	
	
////////////////////////////////////////////////////////////////////////////
	
	
	public Receipt createNewReceipt() {
		if(loggedInEmployee == null) {
			throw new IllegalStateException("No user is logged in");
		} else {
			currentReceipt = new Receipt(receiptIdCounter, customer);
			receiptIdCounter++;
			log(String.format("Created new receipt #%d", currentReceipt.getId()));
			return currentReceipt;
		}
	}

	public void scanItem(EAN ean) {
		Item item = null;

		for (EAN ean2 : itemCollection.keySet()) {
			if (ean2.equals(ean)) {
				item = itemCollection.get(ean2);
				currentReceipt.addItem(item, 1);
			}
		}
		if (item == null) {
			throw new IllegalArgumentException("There is no item with that ean code");
		}
	}

	public void scanRemoveItem(EAN ean) {

		Item item = null;

		for (EAN ean2 : itemCollection.keySet()) {
			if (ean2.equals(ean)) {
				item = itemCollection.get(ean2);
				currentReceipt.addItem(item, -1);
			}
		}
		
		if (item == null) {
			throw new IllegalArgumentException("There is no item with that ean code");
		}
	}
	

	public void scanReturnItem(int receiptId, EAN ean) {

		if (!receiptHistory.containsKey(receiptId)) {
			throw new IllegalArgumentException("There is no receipt with that id");
		}
		scanRemoveItem(ean);
	}
	

	public void unparkReceipt(int id) {
//		if (currentReceipt != null) {
//			throw new IllegalStateException("There is an active receipt already");
//		}
		if (!parkedReceipts.containsKey(id)) {
			throw new IllegalStateException("There is no parked receipt with that id");
		}
		currentReceipt = parkedReceipts.get(id);
		parkedReceipts.remove(id);
		log(String.format("Resumed receipt #%d", currentReceipt.getId()));
	}
	
	
	////////////////////////////////////////////////////////////////////////////
	
	public void finishReceipt() {
		log(String.format("Finished receipt #%d", currentReceipt.getId()));
		printReceipt();
		receiptHistory.put(currentReceipt.getId(), currentReceipt);
		currentReceipt = null;
	}
	

	public void cancelPurchase() {
		log(String.format("Cancelled purchase of receipt #%d", currentReceipt.getId()));
		currentReceipt = null;
	}

	public void parkReceipt() {
		log(String.format("Parked receipt #%d", currentReceipt.getId()));
		parkedReceipts.put(currentReceipt.getId(), currentReceipt);
		currentReceipt = null;
	}
	
	
	////////////////Mock objekt////////////////////////////////////////
	
	public boolean printReceipt() {
		return receiptPrinter.printActiveReceipt();
	}
	
	public int getHowManyPaperLeft() {
		return receiptPrinter.getPaperLeft();
	}
	
	
	public boolean printDailyReceipts(){
		return receiptPrinter.printDailyReceipts();
	}
	
//	public boolean printActiveReceipt() {
//		return receiptPrinter.printReceipt(currentReceipt);
//	}
	
	
	////////////////////////////////////////////////////////



	
	public HashMap<Integer, Employee> getEmployees(){
		return allEmployees;
	}
	
	
	public void LogInEmployee(int id) {

		if (allEmployees.containsKey(id)) {
			loggedInEmployee = allEmployees.get(id);
		} else {
			throw new IllegalArgumentException("No employee with that id");
		}
	}
	

	public Employee getloggedInEmployee(){
		return loggedInEmployee;
	}
	

	public void logOutEmployee() {
		if(loggedInEmployee == null) {
			throw new IllegalStateException("No employee logged in");
		}
		loggedInEmployee = null;
	}
	

	public Employee addEmployee(String name) {
		for (Employee employee : allEmployees.values()) {
			if (employee.getName().equals(name)) {
				throw new IllegalArgumentException("Employee Already added");
			}
		}
		int id = employeeId;
		employeeId++;
		Employee employee = new Employee(id, name);
		allEmployees.put(id, employee);
		return employee;
	}

	public void removeEmployee(String name) {
		Employee employeeToBeRemoved = null;
		for (Employee employee : allEmployees.values()) {
			if (employee.getName().equals(name)) {
				employeeToBeRemoved = employee;
			}
		}

		if (employeeToBeRemoved == null) {
			throw new IllegalArgumentException("There is no employee with that name");
		}
		allEmployees.remove(employeeToBeRemoved.getId());
	}
	
	
	public String printItemCategories() {
		String itemCategoriesString = "";
		for (ItemCategory itemCategory : itemCategories.values()) {
			itemCategoriesString = itemCategoriesString + itemCategory.getName();
		}
		return itemCategoriesString;
	}

	public Supplier getSupplier(String supplierName) {
		return suppliers.get(supplierName);
	}
	
	
}




