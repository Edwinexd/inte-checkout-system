
package com.agie;

public class Employee {

	private int id;
	private String name;

	public Employee(int id, String name) {
		if (id < 0) {
			throw new IllegalArgumentException("id can not be negative");
		}
		
		if (id > 10000) {
			throw new IllegalArgumentException("id out of range");
		}
		
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

}
