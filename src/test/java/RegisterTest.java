
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.agie.AgeLimit;
import com.agie.Currency;
import com.agie.EAN;
import com.agie.Employee;
import com.agie.Item;
import com.agie.LogLevel;
import com.agie.Logger;
import com.agie.LoggingException;
import com.agie.Money;
import com.agie.Receipt;
import com.agie.ReceiptPrinter;
import com.agie.Register;
import com.agie.Supplier;
import com.agie.VATRate;

@ExtendWith(MockitoExtension.class)
public class RegisterTest {
	
	@Mock
	ReceiptPrinter receiptPrinter;

	@InjectMocks
	Register register = new Register(1);

	
	@Test
	public void testAddValidFruitItemCategory() {
		Register register = new Register(0);
		register.addItemCategory("fruit", VATRate.VAT_12, null);
		assertEquals("fruit", register.printItemCategories());
	}

	@Test
	public void testAddFruitItemCategoryAgain() {
		Register register = new Register(0);
		register.addItemCategory("fruit", VATRate.VAT_12, null);
		assertThrows(IllegalArgumentException.class, () -> {
			register.addItemCategory("fruit", VATRate.VAT_12, null);
		});
	}

	@Test
	public void testAddFruitItemCategoryWithTrailingSpaces() {
		Register register = new Register(0);
		register.addItemCategory("fruit    ", VATRate.VAT_12, null);
		assertEquals("fruit", register.printItemCategories());
	}

	@Test
	public void testAddFruitItemCategoryWithLeadingSpaces() {
		Register register = new Register(0);
		register.addItemCategory("    fruit", VATRate.VAT_12, null);
		assertEquals("fruit", register.printItemCategories());
	}
	
	@Test
	public void testAddFruitItemCategoryUpperCase() {
		Register register = new Register(0);
		register.addItemCategory("FRUIT", VATRate.VAT_12, null);
		assertEquals("fruit", register.printItemCategories());
	}
	
	

	@Test
	public void testAddItemCategoryNoName() {
		Register register = new Register(0);
		assertThrows(IllegalArgumentException.class, () -> {
			register.addItemCategory("", VATRate.VAT_12, null);
		});
	}

	@Test
	public void testAddSupplier() {
		Register register = new Register(0);
		Supplier supplier = register.addSupplier("chiquita");
		assertEquals(supplier, register.getSupplier("chiquita"));
	}

	@Test
	public void testAddSupplierAgain() {
		Register register = new Register(0);
		register.addSupplier("chiquita");
		assertThrows(IllegalArgumentException.class, () -> {
			register.addSupplier("chiquita");
		});
	}

	@Test
	public void testAddSupplierNoName() {
		Register register = new Register(0);
		assertThrows(IllegalArgumentException.class, () -> {
			register.addSupplier("");
		});
	}

	@Test
	public void testAddSupplierOnlySpaces() {
		Register register = new Register(0);
		assertThrows(IllegalArgumentException.class, () -> {
			register.addSupplier("      ");
		});
	}

	@Test
	public void testAddBananaItem() {
		Register register = new Register(0);
		register.addItemCategory("fruit", VATRate.VAT_12, null);
		register.addSupplier("chiquita");
		assertDoesNotThrow(
				() -> register.addItem("banana", 10, "fruit", null, "chiquita", null, 1235678901000L, false));
	}

	@Test
	public void testAddBananaItemAgain() {
		Register register = new Register(0);
		register.addItemCategory("fruit", VATRate.VAT_12, null);
		register.addSupplier("chiquita");
		register.addItem("banana", 10, "fruit", null, "chiquita", null, 1235678901000L, false);
		assertThrows(IllegalArgumentException.class, () -> {
			register.addItem("banana", 10, "fruit", null, "chiquita", null, 1235678901000L, false);
		});
	}

	@Test
	public void testAddBananaItemBeforeSupplier() {
		Register register = new Register(0);
		register.addItemCategory("fruit", VATRate.VAT_12, null);
		assertThrows(IllegalArgumentException.class, () -> {
			register.addItem("banana", 10, "fruit", null, "chiquita", null, 1235678901000L, false);
		});
	}

	@Test
	public void testAddBananaItemBeforeCategory() {
		Register register = new Register(0);
		register.addSupplier("chiquita");
		assertThrows(IllegalArgumentException.class, () -> {
			register.addItem("banana", 10, "fruit", null, "chiquita", null, 1235678901000L, false);
		});
	}

	@Test
	public void testAddItemNoName() {
		Register register = new Register(0);
		register.addItemCategory("fruit", VATRate.VAT_12, null);
		register.addSupplier("chiquita");
		assertThrows(IllegalArgumentException.class, () -> {
			register.addItem("", 10, "fruit", null, "chiquita", null, 1235678901000L, false);
		});
	}

	//////////////////////////////////////////////////////////////////

	@Test
	public void testTryCreateNewReceipt() {
		Register register = new Register(1);
		Employee employee = register.addEmployee("Maria Svensson");
		register.LogInEmployee(employee.getId());

		assertDoesNotThrow(() -> {
			register.createNewReceipt();
		});
	}

	@Test
	public void testLoggInEmployee() {
		Register register = new Register(1);
		Employee employee = register.addEmployee("Maria Svensson");
		register.LogInEmployee(employee.getId());

//		assertDoesNotThrow(() -> {
//		register.LogInEmployee(employee.getId());
//	});
		assertEquals("Maria Svensson", register.getloggedInEmployee().getName());
	}

	@Test
	public void testTryScanningItem() {
		Register register = new Register(1);
		Employee employee = register.addEmployee("Maria Svensson");
		register.LogInEmployee(employee.getId());
		register.createNewReceipt();

		///// item////
		register.addItemCategory("fruits", VATRate.VAT_12, null);
		register.addSupplier("chiquita");
		long eanBanana = 1235678901000L;
		register.addItem("banana", 10, "fruits", null, "chiquita", null, eanBanana, false);
		EAN ean = new EAN(eanBanana);

		assertDoesNotThrow(() -> {
			register.scanItem(ean);
		});
	}

	@Test
	public void testRemoveItemFromReceipt() {
		Register register = new Register(1);
		Employee employee = register.addEmployee("Maria Svensson");
		register.LogInEmployee(employee.getId());
		register.createNewReceipt();
		///// item////
		register.addItemCategory("fruits", VATRate.VAT_12, null);
		register.addSupplier("chiquita");
		long eanBanana = 1235678901000L;
		register.addItem("banana", 10, "fruits", null, "chiquita", null, eanBanana, false);
		EAN ean = new EAN(eanBanana);
		assertDoesNotThrow(() -> {
			register.scanRemoveItem(ean);
		});
	}

	@Test
	public void testTryParkReceipt() {
		Register register = new Register(1);
		Employee employee = register.addEmployee("Maria Svensson");
		register.LogInEmployee(employee.getId());
		register.createNewReceipt();
		///// item////
		register.addItemCategory("fruits", VATRate.VAT_12, null);
		register.addSupplier("chiquita");
		long eanBanana = 1235678901000L;
		register.addItem("banana", 10, "fruits", null, "chiquita", null, eanBanana, false);
		EAN ean = new EAN(eanBanana);
		assertDoesNotThrow(() -> {
			register.parkReceipt();
		});
	}

	@Test
	public void testTryToReturnItem() {
		when(receiptPrinter.printActiveReceipt()).thenReturn(true);
//		Register register = new Register(1);
		Employee employee = register.addEmployee("Maria Svensson");
		register.LogInEmployee(employee.getId());
		register.createNewReceipt();
		int receiptId = register.getCurrentReceipt().getId();
		Receipt receipt = register.getCurrentReceipt();
		///// item////
		register.addItemCategory("fruits", VATRate.VAT_12, null);
		register.addSupplier("chiquita");
		long eanBanana = 1235678901000L;

		register.addItem("banana", 10, "fruits", null, "chiquita", null, eanBanana, false);
		EAN ean = new EAN(eanBanana);
		register.scanItem(ean);
		register.finishReceipt();
		register.createNewReceipt();

		assertDoesNotThrow(() -> {
			register.scanReturnItem(receiptId, ean);
		});
	}

	@Test
	public void testTryCancelPurchase() {
		Register register = new Register(1);
		Employee employee = register.addEmployee("Maria Svensson");
		register.LogInEmployee(employee.getId());
		register.createNewReceipt();
		int receiptId = register.getCurrentReceipt().getId();
		Receipt receipt = register.getCurrentReceipt();
		///// item////
		register.addItemCategory("fruits", VATRate.VAT_12, null);
		register.addSupplier("chiquita");
		long eanBanana = 1235678901000L;

		register.addItem("banana", 10, "fruits", null, "chiquita", null, eanBanana, false);
		EAN ean = new EAN(eanBanana);
		register.scanItem(ean);

		assertDoesNotThrow(() -> {
			register.cancelPurchase();
		});
	}

	@Test
	public void testTryfinishReceipt() {
		
		when(receiptPrinter.printActiveReceipt()).thenReturn(true);
		//Register register = new Register(1);
		Employee employee = register.addEmployee("Maria Svensson");
		register.LogInEmployee(employee.getId());
		register.createNewReceipt();
		int receiptId = register.getCurrentReceipt().getId();
		Receipt receipt = register.getCurrentReceipt();
		///// item////
		register.addItemCategory("fruits", VATRate.VAT_12, null);
		register.addSupplier("chiquita");
		long eanBanana = 1235678901000L;

		register.addItem("banana", 10, "fruits", null, "chiquita", null, eanBanana, false);
		EAN ean = new EAN(eanBanana);
		register.scanItem(ean);

		assertDoesNotThrow(() -> {
			register.finishReceipt();
		});
	}

	@Test
	public void testTryfinishParkedReceipt() {
		Register register = new Register(1);
		Employee employee = register.addEmployee("Maria Svensson");
		register.LogInEmployee(employee.getId());
		register.createNewReceipt();
		int receiptId = register.getCurrentReceipt().getId();
		Receipt receipt = register.getCurrentReceipt();

		register.parkReceipt();

		assertDoesNotThrow(() -> {
			register.unparkReceipt(receiptId);
		});

	}

	@Test
	public void testTryLogOutEmployee() {
		Register register = new Register(1);
		Employee employee = register.addEmployee("Maria Svensson");
		register.LogInEmployee(employee.getId());

		assertDoesNotThrow(() -> {
			register.logOutEmployee();
		});
	}

	@Test
	public void testTryAddEmployee() {
		Register register = new Register(1);
		Employee employee = register.addEmployee("Maria Svensson");
		register.LogInEmployee(employee.getId());

		assertEquals(employee, register.getEmployee(employee.getId()));
	}
	
	
	/////////////////////chatgpt //////////////////////////
    @Test
    void testLogInEmployeeWithExistingId() {
        Register register = new Register(1);
        Employee employee = new Employee(1, "John Doe");
        register.getEmployees().put(1, employee);

        assertDoesNotThrow(() -> register.LogInEmployee(1));
        assertEquals(employee, register.getloggedInEmployee());
    }

    @Test
    void testLogInEmployeeWithNonExistingId() {
        Register register = new Register(1);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> register.LogInEmployee(2));
        assertEquals("No employee with that id", exception.getMessage());
    }

    @Test
    void testAddEmployee() {
        Register register = new Register(1);

        Employee addedEmployee = assertDoesNotThrow(() -> register.addEmployee("John Doe"));
        assertEquals(addedEmployee, register.getEmployee(addedEmployee.getId()));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> register.addEmployee("John Doe"));
        assertEquals("Employee Already added", exception.getMessage());
    }

    @Test
    void testRemoveEmployee() {
        Register register = new Register(1);
        Employee addedEmployee = register.addEmployee("John Doe");

        assertDoesNotThrow(() -> register.removeEmployee("John Doe"));
        assertNull(register.getEmployee(addedEmployee.getId()));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> register.removeEmployee("NonExistentEmployee"));
        assertEquals("There is no employee with that name", exception.getMessage());
    }

    @Test
    void testLogOutEmployee() {
        Register register = new Register(1);

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> register.logOutEmployee());
        assertEquals("No employee logged in", exception.getMessage());

        Employee employee = new Employee(1, "John Doe");
        register.getEmployees().put(1, employee);
        register.LogInEmployee(1);

        assertDoesNotThrow(() -> register.logOutEmployee());
        assertNull(register.getloggedInEmployee());
    }
	
	
	///////////////////////////////////////////////////////

    
	@Test
	public void createNewReceiptLoggedinNullTest() {
		
		Register register = new Register(1);
		Employee employee = register.addEmployee("Maria Svensson");

		assertThrows(IllegalStateException.class, () -> {
			register.createNewReceipt();
		});
	}
	
	
	@Test
	public void scanItemNullTest() {

		Register register = new Register(1);
		Employee employee = register.addEmployee("Maria Svensson");
		register.LogInEmployee(employee.getId());
		register.createNewReceipt();
		
		long eanBanana = 1235678901000L;
		EAN ean = new EAN(eanBanana);

		assertThrows(IllegalArgumentException.class, () -> {
			register.scanItem(ean);
		});
	}
	
	
	@Test
	public void scanRemoveItemNullTest() {
		Register register = new Register(1);
		Employee employee = register.addEmployee("Maria Svensson");
		register.LogInEmployee(employee.getId());
		register.createNewReceipt();
		
		long eanBanana = 1235678901000L;
		EAN ean = new EAN(eanBanana);

		assertThrows(IllegalArgumentException.class, () -> {
			register.scanRemoveItem(ean);
		});
	}
	
	@Test
	public void testTryToReturnItemNullTest() {
//		Register register = new Register(1);
		Employee employee = register.addEmployee("Maria Svensson");
		register.LogInEmployee(employee.getId());
		register.createNewReceipt();
		int receiptId = 1000;
		Receipt receipt = register.getCurrentReceipt();
		///// item////

		long eanBanana = 1235678901000L;
		EAN ean = new EAN(eanBanana);

		assertThrows(IllegalArgumentException.class, () -> {
			register.scanReturnItem(receiptId, ean);
		});
		
	}
	
	
	@Test
	public void unparkReceiptNullTest2() {
		Register register = new Register(1);
		Employee employee = register.addEmployee("Maria Svensson");
		register.LogInEmployee(employee.getId());
		register.createNewReceipt();
		int receiptId = register.getCurrentReceipt().getId();
		Receipt receipt = register.getCurrentReceipt();
		register.parkReceipt();

		assertThrows(IllegalStateException.class, () -> {
			register.unparkReceipt(1000);
		});

	}
	
	@Test
	public void unparkReceiptNullTest3() {
		Register register = new Register(1);
		assertThrows(IllegalStateException.class, () -> {
			register.unparkReceipt(1000);
		});
	}


	
	@Test
	public void removeItemFromItemCollectionTest() {
		Register register = new Register(1);
		Employee employee = register.addEmployee("Maria Svensson");
		register.LogInEmployee(employee.getId());
		register.createNewReceipt();
		///// item////
		register.addItemCategory("fruits", VATRate.VAT_12, null);
		register.addSupplier("chiquita");
		long eanBanana = 1235678901000L;
		register.addItem("banana", 10, "fruits", null, "chiquita", null, eanBanana, false);
		EAN ean = new EAN(eanBanana);
		
		assertDoesNotThrow(() -> {
			register.removeItem(ean);
		});
		
	}
	
	
	@Test
	public void removeItemFromItemCategoriesTest2() {
		Register register = new Register(1);
		Employee employee = register.addEmployee("Maria Svensson");
		register.LogInEmployee(employee.getId());
		register.createNewReceipt();
		///// item////
		register.addItemCategory("fruits", VATRate.VAT_12, null);
		register.addSupplier("chiquita");
		long eanBanana = 1235678901000L;
		register.addItem("banana", 10, "fruits", null, "chiquita", null, eanBanana, false);
		EAN ean = new EAN(eanBanana);
		
		assertDoesNotThrow(() -> {
			register.removeItemCategory("fruits");
		});
	}
	
	

	@Test
	public void removeItemFromItemCategoriesNullTest3() {
		Register register = new Register(1);
		assertThrows(IllegalArgumentException.class, () -> {
			register.removeItemCategory("fruits");
		});
		
	}
	
	
	@Test
	public void removeItemFromItemCollectionNullTest() {
		Register register = new Register(1);
		assertThrows(IllegalArgumentException.class, () -> {
			register.removeItem(null);
		});
	}
	
	
	@Test
	public void removeSupplierTest() {
		Register register = new Register(0);
		Supplier supplier = register.addSupplier("chiquita");
		assertDoesNotThrow(() -> {
			register.removeSupplier("chiquita");
		});
	}
	
	@Test
	public void removeSupplierNullTest() {
		Register register = new Register(0);
		assertThrows(IllegalArgumentException.class, () -> {
			register.removeSupplier("chiquita");
		});
	}
	

	
	
	////////////////////////////////////////////////////////////////////////////

	
	// ---------------------------------------------------------------------------------------------

	// AI Generated tests for testing logging within the register class
	// the logger class is mocked and dependency injected into the register class
	// to ensure that Logger is being called correctly

	// logs to be tested:
	// * created new receipt
	// * cancelled purchase of receipt
	// * parked receipt
	// * finished receipt
	// * resumed receipt

	// refactored manually
	private Register setupRegisterWithLogger(Logger logger) throws LoggingException {
		Register register = new Register(1, logger);
		Employee employee = register.addEmployee("Maria Svensson");
		register.LogInEmployee(employee.getId());
		register.createNewReceipt();
		return register;
	}

	@Test
	public void createReceiptIsLogged() throws LoggingException {
		Logger logger = mock(Logger.class);
		Register register = setupRegisterWithLogger(logger);
		int receiptId = register.getCurrentReceipt().getId();
		verify(logger, times(1)).log(LogLevel.INFO, String.format("Created new receipt #%d", receiptId));
	}

	@Test
	public void cancelPurchaseIsLogged() throws LoggingException {
		Logger logger = mock(Logger.class);
		Register register = setupRegisterWithLogger(logger);
		// manually swapped order of cancel and get id or null pointer exception occurs
		int receiptId = register.getCurrentReceipt().getId();
		register.cancelPurchase();
		verify(logger, times(1)).log(LogLevel.INFO, String.format("Cancelled purchase of receipt #%d", receiptId));
	}

	@Test
	public void parkReceiptIsLogged() throws LoggingException {
		Logger logger = mock(Logger.class);
		Register register = setupRegisterWithLogger(logger);
		int receiptId = register.getCurrentReceipt().getId();
		register.parkReceipt();
		verify(logger, times(1)).log(LogLevel.INFO, String.format("Parked receipt #%d", receiptId));
	}

	@Test
	public void finishReceiptIsLogged() throws LoggingException {
		Logger logger = mock(Logger.class);
		Register register = setupRegisterWithLogger(logger);
		int receiptId = register.getCurrentReceipt().getId();
		register.finishReceipt();
		verify(logger, times(1)).log(LogLevel.INFO, String.format("Finished receipt #%d", receiptId));
	}

	@Test
	public void unparkReceiptIsLogged() throws LoggingException {
		Logger logger = mock(Logger.class);
		Register register = setupRegisterWithLogger(logger);
		int receiptId = register.getCurrentReceipt().getId();
		register.parkReceipt();
		register.unparkReceipt(receiptId);
		verify(logger, times(1)).log(LogLevel.INFO, String.format("Resumed receipt #%d", receiptId));
	}


	// Stop Copilot/GPT 3.5 Turbo generated tests
	// ---------------------------------------------------------------------------------------------
}





