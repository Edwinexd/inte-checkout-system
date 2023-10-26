
package com.agie;

import java.util.Objects;

public class Employee {

	
	private int id;
	private String name;
	private static final int EMPLOYEE_MAX_ID = 10000;
	private static final int EMPLOYEE_NAME_MAX_LENGHT = 40;

	public Employee(int id, String name) {
		if (id < 0) {
			throw new IllegalArgumentException("id can not be negative");
		}
		
		if (id > EMPLOYEE_MAX_ID) {
			throw new IllegalArgumentException("id out of range");
		}

		this.id = id;
		
		if (name == null) {
			throw new IllegalArgumentException("name can't be null");
		}
		
		if (name == "") {
			throw new IllegalArgumentException("name can't be empty");
		}
		
		if (name.length() > EMPLOYEE_NAME_MAX_LENGHT) {
			throw new IllegalArgumentException("name too long");
		}
		
		this.name = name;
		
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Employee) {
			Employee e = (Employee) obj;
			return this.id == e.id && this.name.equals(e.name);
		}
		else {
			return false;
		}
	}
	

}



