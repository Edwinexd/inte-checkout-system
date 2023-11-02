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
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;
import com.agie.Employee;
import com.agie.Register;
import com.agie.VATRate;

public class EmployeeTest {
	
	
	
	@Test
	public void testCreateEmployee(){
		assertDoesNotThrow(() -> {
			new Employee(5500, "Maria Svensson");
		});
	}
	
	@Test
	public void testGetName(){
		Employee employee = new Employee(5500, "Maria Svensson");
		assertEquals(employee.getName(), "Maria Svensson");
	}
	
	@Test
	public void nameNullTest() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Employee(5500, null);
		});
	}
	
	@Test
	public void nameEmptyTest() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Employee(5500, "");
		});
	}
	
	@Test
	public void nameTooLongTest() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Employee(5500, "MariaEvaMaria SvenssonJohanssonSvenssonSvenssonSvenssonSvensson");
		});
	}
	
	
	@Test
	public void testCreateEmployeeTooLongId(){
		assertThrows(IllegalArgumentException.class, () -> {
			new Employee(500000, "Maria Svensson");
		});
	}
	
	@Test
	public void testCreateEmployeeNegativeIdNumber(){
		assertThrows(IllegalArgumentException.class, () -> {
			new Employee(-50, "Maria Svensson");
		});
	}
	
	@Test
	public void testDoesEqualsMethodWork(){
		Employee employee = new Employee(5500, "Maria Svensson");
		Employee employee2 = new Employee(5500, "Maria Svensson");
		boolean areTheyEqual = false;
		if(employee.equals(employee2)) {
			areTheyEqual = true;
		}
		
		assertEquals(true, areTheyEqual);
	}
	
	@Test
	public void equalTest(){
		Employee employee = new Employee(5500, "Maria Svensson");
		Employee employee2 = new Employee(5500, "Maria Svensson");
		boolean areTheyEqual = false;
		if(employee.equals(employee2)) {
			areTheyEqual = true;
		}
		
		assertEquals(true, areTheyEqual);
	}

	@Test
	public void testDoEmployeeIdMatch(){
		Employee employee = new Employee(5500, "Maria Svensson");
		assertEquals(employee.getId(), 5500);
	}
	
	
	@Test
	public void isNotEqualtest(){
		Employee employee = new Employee(5500, "Maria Svensson");
		Register register = new Register(1);
		boolean areTheyEqual = false;
		if(employee.equals(register)) {
			areTheyEqual = true;
		}
		assertEquals(false, areTheyEqual);
	}
	
	
	
	
	
}






