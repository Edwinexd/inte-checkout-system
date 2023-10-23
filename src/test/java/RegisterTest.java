
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

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
import com.agie.Register;
import com.agie.VATRate;


public class RegisterTest {
	
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
		register.addSupplier("chiquita");
		assertEquals("chiquita", register.printSuppliers());
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
				() -> register.addItem("banana", 10, "fruit", null, "chiquita", null, 1845678901001l, false));
	}

	@Test
	public void testAddBananaItemAgain() {
		Register register = new Register(0);
		register.addItemCategory("fruit", VATRate.VAT_12, null);
		register.addSupplier("chiquita");
		register.addItem("banana", 10, "fruit", null, "chiquita", null, 1845678901001l, false);
		assertThrows(IllegalArgumentException.class, () -> {
			register.addItem("banana", 10, "fruit", null, "chiquita", null, 1845678901001l, false);
		});
	}

	@Test
	public void testAddBananaItemBeforeSupplier() {
		Register register = new Register(0);
		register.addItemCategory("fruit", VATRate.VAT_12, null);
		assertThrows(IllegalArgumentException.class, () -> {
			register.addItem("banana", 10, "fruit", null, "chiquita", null, 1845678901001l, false);
		});
	}

	@Test
	public void testAddBananaItemBeforeCategory() {
		Register register = new Register(0);
		register.addSupplier("chiquita");
		assertThrows(IllegalArgumentException.class, () -> {
			register.addItem("banana", 10, "fruit", null, "chiquita", null, 1845678901001l, false);
		});
	}

	@Test
	public void testAddItemNoName() {
		Register register = new Register(0);
		register.addItemCategory("fruit", VATRate.VAT_12, null);
		register.addSupplier("chiquita");
		assertThrows(IllegalArgumentException.class, () -> {
			register.addItem("", 10, "fruit", null, "chiquita", null, 1845678901001l, false);
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
		long eanBanana = 1845678901001l;
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
		long eanBanana = 1845678901001l;
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
		long eanBanana = 1845678901001l;
		register.addItem("banana", 10, "fruits", null, "chiquita", null, eanBanana, false);
		EAN ean = new EAN(eanBanana);
		assertDoesNotThrow(() -> {
			register.parkReceipt();
		});
	}

	@Test
	public void testTryToReturnItem() {
		Register register = new Register(1);
		Employee employee = register.addEmployee("Maria Svensson");
		register.LogInEmployee(employee.getId());
		register.createNewReceipt();
		int receiptId = register.getCurrentReceipt().getId();
		Receipt receipt = register.getCurrentReceipt();
		///// item////
		register.addItemCategory("fruits", VATRate.VAT_12, null);
		register.addSupplier("chiquita");
		long eanBanana = 1845678901001l;

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
		long eanBanana = 1845678901001l;

		register.addItem("banana", 10, "fruits", null, "chiquita", null, eanBanana, false);
		EAN ean = new EAN(eanBanana);
		register.scanItem(ean);

		assertDoesNotThrow(() -> {
			register.cancelPurchase();
		});
	}

	@Test
	public void testTryfinishReceipt() {
		Register register = new Register(1);
		Employee employee = register.addEmployee("Maria Svensson");
		register.LogInEmployee(employee.getId());
		register.createNewReceipt();
		int receiptId = register.getCurrentReceipt().getId();
		Receipt receipt = register.getCurrentReceipt();
		///// item////
		register.addItemCategory("fruits", VATRate.VAT_12, null);
		register.addSupplier("chiquita");
		long eanBanana = 1845678901001l;

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
