
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






