
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;
import com.agie.Employee;
import com.agie.Register;

public class TestEmployee {
	
	
	@Test
	public void createEmployee(){
		assertDoesNotThrow(() -> {
			new Employee(5500, "Maria Svensson");
		});
	}
	
	
	@Test
	public void createEmployeeTooLongId(){
		assertThrows(IllegalArgumentException.class, () -> {
			new Employee(500000, "Maria Svensson");
		});
	}
	
	@Test
	public void createEmployeeNegativeIdNumber(){
		assertThrows(IllegalArgumentException.class, () -> {
			new Employee(-50, "Maria Svensson");
		});
	}
	
	
	@Test
	public void doesEqualsMethodWork(){
		Employee employee = new Employee(5500, "Maria Svensson");
		Employee employee2 = new Employee(5500, "Maria Svensson");
		boolean areTheyEqual = false;
		if(employee.equals(employee2)) {
			areTheyEqual = true;
		}
		
		assertEquals(true, areTheyEqual);
	}
	

	@Test
	public void doEmployeeIdMatch(){
		Employee employee = new Employee(5500, "Maria Svensson");
		assertEquals(employee.getId(), 5500);
	}
	
	
	
	
	
}






