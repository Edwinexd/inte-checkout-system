
package com.agie;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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

	public Register(int registerNr) {
		this.registerNumber = registerNr;
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

	public void removeItem(String name) {
		name = formatStringInput(name);
		if (itemCollection.get(name) == null) {
			throw new IllegalArgumentException("No Item with that name");
		}
		itemCategories.remove(name);
	}

	public Receipt createNewReceipt() {
		if(loggedInEmployee == null) {
			throw new IllegalStateException("No user is logged in");
		} else {
			currentReceipt = new Receipt(receiptIdCounter, customer);
			receiptIdCounter++;
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

	public void cancelPurchase() {
		currentReceipt = null;
	}

	public void parkReceipt() {
		parkedReceipts.put(currentReceipt.getId(), currentReceipt);
		currentReceipt = null;
	}

	public String printOutReceipt() {
		return currentReceipt.toString();
	}

	public void finishReceipt() {
		printOutReceipt();
		receiptHistory.put(currentReceipt.getId(), currentReceipt);
		currentReceipt = null;
	}

	public void unparkReceipt(int id) {
		if (currentReceipt != null) {
			throw new IllegalStateException("There is an active receipt already");
		}
		if (!parkedReceipts.containsKey(id)) {
			throw new IllegalStateException("There is no parked receipt with that id");
		}
		currentReceipt = parkedReceipts.get(id);
		parkedReceipts.remove(id);
	}

	public void LogInEmployee(int id) {

		if (allEmployees.containsKey(id)) {
			loggedInEmployee = allEmployees.get(id);
		} else {
			throw new IllegalArgumentException("No employee with that id");
		}
	}

	public Employee getloggedInEmployee() {
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
	
	public int getRegisterNr() {
		return registerNumber;
	}

	public String printReceiptHistory() {
		return receiptHistory.toString();
	}

	public String printParkedReceipts() {
		return parkedReceipts.toString();
	}

	// TODO: Se över namngivningen
	public String printEmployeeList() {
		return allEmployees.toString();
	}
	
	public String printItems() {
		String itemsString = "";
		for (Item item : itemCollection.values()) {
			itemsString = itemsString + item.toString();
		}
		return itemsString;
	}

	public String printItemCategories() {
		String itemCategoriesString = "";
		for (ItemCategory itemCategory : itemCategories.values()) {
			itemCategoriesString = itemCategoriesString + itemCategory.getName();
		}
		return itemCategoriesString;
	}

	public String printSuppliers() {
		String suppliersString = "";
		for (Supplier supplier : suppliers.values()) {
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

	public String printCustomer() {
		return customer.toString();
	}

}
