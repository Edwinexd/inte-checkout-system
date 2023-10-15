
package com.agie;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Register {

	private int registerNumber;
	private HashMap<EAN, Item> itemList = new HashMap();
	private HashMap<Integer, Receipt> receiptHistory = new HashMap();
	private HashMap<Integer, Receipt> parkedReceipts = new HashMap();
	private HashMap<Integer, Employee> employeeList = new HashMap();
	private HashMap<String, ItemCategory> itemCategories = new HashMap();
	private HashMap<String, Supplier> suppliers = new HashMap<>();
	private Receipt currentReceipt;
	private Employee loggedInEmployee;
	private boolean loggedIn = false;
	private Customer customer;
	private int currentReceiptId;

	public Register(int registerNr) {
		this.registerNumber = registerNr;
		//addTestData();
	}
	
	public Employee getEmployee(int id) {
        return employeeList.get(id);
    }

	
	private String formatStringInput(String theString) {
		theString = theString.trim();
		theString = theString.toLowerCase();
		if (theString.isEmpty()) {
			throw new IllegalArgumentException("String is empty");
		}
		return theString;
	}
	
	public void addItemCategory(String name, VATRate vatRate, AgeLimit ageLimit) {
		name = formatStringInput(name);
		if (itemCategories.get(name) != null) {
			throw new IllegalArgumentException("Itemcategory already created");
		}
		itemCategories.put(name, new ItemCategory(name, vatRate, ageLimit));
	}
	
	public void removeItemCategory(String name) {
		name = formatStringInput(name);
		if (itemCategories.get(name) == null) {
			throw new IllegalArgumentException("No item category with that name");
		}
		itemCategories.remove(name);
	}
	
	public void addSupplier(String name) {
		name = formatStringInput(name);
		if (suppliers.get(name) != null) {
			throw new IllegalArgumentException("Supplier already created");
		}
		suppliers.put(name, new Supplier(name));
	}
	
	public void removeSupplier(String name) {
		name = formatStringInput(name);
		if (suppliers.get(name) == null) {
			throw new IllegalArgumentException("No supplier with that name");
		}
		suppliers.remove(name);
	}
	
	public void addItem(String name, int costInSek, String itemCategory, Deposit deposit, String supplier, AgeLimit ageLimit, long eanNumber, boolean weightBased) {
		
		name = formatStringInput(name);
		EAN ean = new EAN(eanNumber);
		if (itemList.get(ean) != null) {
			throw new IllegalArgumentException("Item with that name already created");
		}
		if (itemCategories.get(itemCategory) == null) {
			throw new IllegalArgumentException("Itemcategory does not exist");
		}
		if (suppliers.get(supplier) == null) {
			throw new IllegalArgumentException("Supplier does not exist");
		}
		Item item = new Item(name, 
				new Money(costInSek, Currency.SEK), 
				itemCategories.get(itemCategory),
				deposit,
				suppliers.get(supplier), 
				ean, 
				weightBased);
		itemList.put(ean, item);
	}
	
	public void removeItem(String name) {
		name = formatStringInput(name);
		if (itemList.get(name) == null) {
			throw new IllegalArgumentException("No Item with that name");
		}
		itemCategories.remove(name);
	}
	
	public int getRegisterNr() {
		return registerNumber;
	}

	public String printReceiptHistory() {
		return receiptHistory.toString();
	}

	public String printParkedReceipts() {
		return parkedReceipts.toString();
	}

	public String printEmployeeList() {
		return employeeList.toString();
	}
	
	public String printItems() {
		String itemsString = "";
		for (Item item: itemList.values()) {
			itemsString = itemsString + item.toString();
		}
		return itemsString;
	}

	public String printItemCategories() {
		String itemCategoriesString = "";
		for (ItemCategory itemCategory: itemCategories.values()) {
			itemCategoriesString = itemCategoriesString + itemCategory.getName();
		}
		return itemCategoriesString;
	}

	public String printSuppliers() {
		String suppliersString = "";
		for (Supplier supplier: suppliers.values()) {
			suppliersString = suppliersString + supplier.getName();
		}
		return suppliersString;
	}

	public String printCurrentReceipt() {
		return currentReceipt.toString();
	}

	public String printLoggedInEmployee() {
		return loggedInEmployee.toString();
	}

	public String printLoggedIn() {
		return String.valueOf(loggedIn);
	}

	public String printCustomer() {
		return customer.toString();
	}

	public void createNewReceipt() {
		if (loggedIn) {
			currentReceipt = new Receipt(currentReceiptId, customer);
			currentReceiptId++;
		}
	}

	public void removeItemList() {
		itemList = null;
	}

	public void scanItem(EAN ean) {
		Item item = itemList.get(ean);
		currentReceipt.addItem(item, 1);
	}

	public void scanRemoveItem(EAN ean) {
		Item item = itemList.get(ean);
		currentReceipt.addItem(item, -1);
	}

	public void returnItem(int receiptId, Item item) {

		for (Integer id : receiptHistory.keySet()) {
			if (id == receiptId) {
				currentReceipt = receiptHistory.get(id);
			}
		}
		currentReceipt.addItem(item, -1);
		currentReceipt = null;

	}

	public void cancelPurchase() {
		currentReceipt = null;
	}

	public void paymentMethod() {

	}

	public void parkReceipt() {
		parkedReceipts.put(currentReceipt.getId(), currentReceipt);
		currentReceipt = null;
	}

	public void getParkedReceipt_makeParkedReceiptIntoCurrentReceipt(int receiptId) {
		for (Integer i : parkedReceipts.keySet()) {
			if (i == receiptId) {
				currentReceipt = parkedReceipts.get(i);
			}
		}
	}

	public void removeParkedReceipt(int receiptId) {
		HashMap<Integer, Receipt> newMap = new HashMap<>();

		for (Integer i : parkedReceipts.keySet()) {
			if (!(i == receiptId)) {
				newMap.put(i, parkedReceipts.get(i));
			}
			parkedReceipts = newMap;
		}
	}

	public void printOutReceipt() {
		currentReceipt.toString();
	}

	public void finishReceipt() {
		printOutReceipt();
		receiptHistory.put(currentReceipt.getId(), currentReceipt);
		currentReceipt = null;
	}

	public void finishParkedReceipt() {
		receiptHistory.put(currentReceipt.getId(), currentReceipt);
		removeParkedReceipt(currentReceipt.getId());
		printOutReceipt();
		currentReceipt = null;
	}

	public void LogInEmployee(Employee employee) {
		for (Integer i : employeeList.keySet()) {
			if (employeeList.get(i) == employee) {
				loggedIn = true;
				loggedInEmployee = employee;
			}
		}
	}

	public Employee getloggedInEmployee() {
		return loggedInEmployee;
	}

	public void logOutEmployee() {
		loggedIn = false;
	}

	public void addEmployee(int id, String name) {
		Employee employee = new Employee(name);
		employeeList.put(id, employee);
	}

}
