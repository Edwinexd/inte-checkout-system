import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import com.agie.Employee;

public class TestEmployee {
	
	
	@Test
	public void createEmployee(){
		Employee employee;
		assertThrows(IllegalArgumentException.class, () -> {
			new Employee(550066, "Maria Svensson");
		});
	}
	
	
	@Test
	public void createEmployeeTooLongId(){
		assertThrows(IllegalArgumentException.class, () -> {
			new Employee(55006600, "Maria Svensson");
		});
	}
	
	@Test
	public void createEmployeeErrorName(){
		assertThrows(IllegalArgumentException.class, () -> {
			new Employee(550066, "Maria432Svensson");
		});
	}
	
	
	@Test
	public void doesEmployeeExist(){
		int Id = 550066;
		assertThrows(IllegalArgumentException.class, () -> {
			employeeList.getEmployee(550066, "Maria Svensson");
		});
	}
	
	@Test
	public void doEmployeeIdMatch(){
		Employee employee = new Employee(550066, "Maria Svensson");
		assertEquals(employee.getId(), 550066);
	}
	
	
}
