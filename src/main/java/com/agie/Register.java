
package com.agie;

import java.util.ArrayList;
import java.util.HashMap;

public class Register{


	private int registerNr;
	private HashMap<Integer, Receipt> receiptHistory;
	private HashMap<Integer, Receipt> parkedReceipts;
	private HashMap<Integer, Employee> employeeList;
	
	private Receipt currentReceipt;
	private int loggedInEmployeeId;
	private boolean loggedIn = false;

	
	public Register(int registerNr) {
		this.registerNr = registerNr;
	}

	
	public void LogInEmployee(int id) {
		for(Integer i : employeeList.keySet()) {
			if(id == i) {
				loggedIn = true;
				loggedInEmployeeId = id;
			}
		}
	}
	
	public int getloggedInEmployeeId() {
		return loggedInEmployeeId;
	}
	

	public void logOutEmployee(){
		loggedIn = false;
	}
	
	public void addEmployee(int id, String name) {
		Employee employee = new Employee(id, name);
		employeeList.put(id, employee);
	}
	
	
	public void createNewReceipt(int a, String b) {
		currentReceipt = new Receipt(loggedInEmployeeId, a, b);
	}
	
	public void cancelPurchase(){
		currentReceipt = null;
	}
	
	public void finishedReceipt() {
		receiptHistory.put(currentReceipt.getId(), currentReceipt);
		currentReceipt = null;
	}
	
    // scan item
	public void scanItem(Item item) {
		currentReceipt.addItem(item);
	}
	
	public void removeItem(int id){
		currentReceipt.removeItem(id);
	}
	
	public void returnItem(int id){
		for(Integer i : receiptHistory.keySet()) {
			if(i == id) {
				currentReceipt = receiptHistory.get(i);
				currentReceipt.removeItem(id);
				currentReceipt = null;
			}
		}
	}
	
	
	public void parkReceipt() {
		parkedReceipts.put(currentReceipt.getId(), currentReceipt);
		currentReceipt = null;
	}
	
	public void getParkedReceipt(int id) {
		for(Integer i : parkedReceipts.keySet()) {
			if(i == id) {
				currentReceipt = parkedReceipts.get(i);
			}
		}
	}
	

	public void removeParkedReceipt(int receiptId) {

		HashMap<Integer, Receipt> tempMap = new HashMap<>();

		for (Integer i : parkedReceipts.keySet()) {
			if (!(i == receiptId)) {
				tempMap.put(i, parkedReceipts.get(i));
			}
			parkedReceipts = tempMap;
		}
	}
	
	
	public void finishedParkedReceipt() {
		receiptHistory.put(currentReceipt.getId(), currentReceipt);
		removeParkedReceipt(currentReceipt.getId());
		currentReceipt = null;
	}
	
	
	
	public void paymentMethod() {
		
	}
	
	
//	public ArrayList<Receipt> getReceitsByEmployee(int id){
//	}
	

	
}










