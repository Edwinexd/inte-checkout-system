
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import com.agie.AgeLimit;
import com.agie.Currency;
import com.agie.EAN;
import com.agie.Employee;
import com.agie.Item;
import com.agie.Money;
import com.agie.Receipt;
import com.agie.Register;
import com.agie.VATRate;


public class TestRegister {
	
	@Test
	public void addValidFruitItemCategory() {
		Register register = new Register(0);
		register.addItemCategory("fruit", VATRate.VAT_12, null);
		assertEquals("fruit", register.printItemCategories());
	}

	@Test
	public void addFruitItemCategoryAgain() {
		Register register = new Register(0);
		register.addItemCategory("fruit", VATRate.VAT_12, null);
		assertThrows(IllegalArgumentException.class, () -> {
			register.addItemCategory("fruit", VATRate.VAT_12, null);
		});
	}

	@Test
	public void addFruitItemCategoryWithTrailingSpaces() {
		Register register = new Register(0);
		register.addItemCategory("fruit    ", VATRate.VAT_12, null);
		assertEquals("fruit", register.printItemCategories());
	}

	@Test
	public void addFruitItemCategoryWithLeadingSpaces() {
		Register register = new Register(0);
		register.addItemCategory("    fruit", VATRate.VAT_12, null);
		assertEquals("fruit", register.printItemCategories());
	}

	@Test
	public void addFruitItemCategoryUpperCase() {
		Register register = new Register(0);
		register.addItemCategory("FRUIT", VATRate.VAT_12, null);
		assertEquals("fruit", register.printItemCategories());
	}

	@Test
	public void addItemCategoryNoName() {
		Register register = new Register(0);
		assertThrows(IllegalArgumentException.class, () -> {
			register.addItemCategory("", VATRate.VAT_12, null);
		});
	}

	@Test
	public void addSupplier() {
		Register register = new Register(0);
		register.addSupplier("chiquita");
		assertEquals("chiquita", register.printSuppliers());
	}

	@Test
	public void addSupplierAgain() {
		Register register = new Register(0);
		register.addSupplier("chiquita");
		assertThrows(IllegalArgumentException.class, () -> {
			register.addSupplier("chiquita");
		});
	}
	
	@Test
	public void addSupplierNoName() {
		Register register = new Register(0);
		assertThrows(IllegalArgumentException.class, () -> {
			register.addSupplier("");
		});
	}
	
	@Test
	public void addSupplierOnlySpaces() {
		Register register = new Register(0);
		assertThrows(IllegalArgumentException.class, () -> {
			register.addSupplier("      ");
		});
	}

	@Test
	public void addBananaItem() {
		Register register = new Register(0);
		register.addItemCategory("fruit", VATRate.VAT_12, null);
		register.addSupplier("chiquita");
		register.addItem("banana", 10, "fruit", null, "chiquita", null, 1845678901001l, false);
		assertEquals("banana - 10,00 kr", register.printItems());
	}

	@Test
	public void addBananaItemAgain() {
		Register register = new Register(0);
		register.addItemCategory("fruit", VATRate.VAT_12, null);
		register.addSupplier("chiquita");
		register.addItem("banana", 10, "fruit", null, "chiquita", null, 1845678901001l, false);
		assertThrows(IllegalArgumentException.class, () -> {
			register.addItem("banana", 10, "fruit", null, "chiquita", null, 1845678901001l, false);
		});
	}

	@Test
	public void addBananaItemBeforeSupplier() {
		Register register = new Register(0);
		register.addItemCategory("fruit", VATRate.VAT_12, null);
		assertThrows(IllegalArgumentException.class, () -> {
			register.addItem("banana", 10, "fruit", null, "chiquita", null, 1845678901001l, false);
		});
	}

	@Test
	public void addBananaItemBeforeCategory() {
		Register register = new Register(0);
		register.addSupplier("chiquita");
		assertThrows(IllegalArgumentException.class, () -> {
			register.addItem("banana", 10, "fruit", null, "chiquita", null, 1845678901001l, false);
		});
	}

	@Test
	public void addItemNoName() {
		Register register = new Register(0);
		register.addItemCategory("fruit", VATRate.VAT_12, null);
		register.addSupplier("chiquita");
		assertThrows(IllegalArgumentException.class, () -> {
			register.addItem("", 10, "fruit", null, "chiquita", null, 1845678901001l, false);
		});
	}

	//////////////////////////////////////////////////////////////////
	
	
	@Test
	public void tryCreateNewReceipt(){
		Register register = new Register(1);
		Employee employee = register.addEmployee("Maria Svensson");
		register.LogInEmployee(employee.getId());
		
		assertDoesNotThrow(() -> {
			register.createNewReceipt();
		});
	}
	

	
	@Test
	public void loggInEmployee(){
		Register register = new Register(1);
		Employee employee = register.addEmployee("Maria Svensson");
		register.LogInEmployee(employee.getId());
		
//		assertDoesNotThrow(() -> {
//		register.LogInEmployee(employee.getId());
//	});
		assertEquals("Maria Svensson", register.getloggedInEmployee().getName());
	}
	
	
	@Test
	public void tryScanningItem(){
		Register register = new Register(1);
		Employee employee = register.addEmployee("Maria Svensson");
		register.LogInEmployee(employee.getId());
		register.createNewReceipt();
		
		/////item////
		register.addItemCategory("fruits", VATRate.VAT_12, null);
		register.addSupplier("chiquita");
		long eanBanana =  1845678901001l;
		register.addItem("banana", 10, "fruits", null, "chiquita", null, eanBanana, false);
		EAN ean =  new EAN(eanBanana);
		
		assertDoesNotThrow(() -> {
			register.scanItem(ean);
		});
	}
	
	
	@Test
	public void removeItemFromReceipt(){
		Register register = new Register(1);
		Employee employee = register.addEmployee("Maria Svensson");
		register.LogInEmployee(employee.getId());
		register.createNewReceipt();
		/////item////
		register.addItemCategory("fruits", VATRate.VAT_12, null);
		register.addSupplier("chiquita");
		long eanBanana =  1845678901001l;
		register.addItem("banana", 10, "fruits", null, "chiquita", null, eanBanana, false);
		EAN ean =  new EAN(eanBanana);
		assertDoesNotThrow(() -> {
			register.scanRemoveItem(ean); 
		});
	}
	
	@Test
	public void tryParkReceipt(){
		Register register = new Register(1);
		Employee employee = register.addEmployee("Maria Svensson");
		register.LogInEmployee(employee.getId());
		register.createNewReceipt();
		/////item////
		register.addItemCategory("fruits", VATRate.VAT_12, null);
		register.addSupplier("chiquita");
		long eanBanana =  1845678901001l;
		register.addItem("banana", 10, "fruits", null, "chiquita", null, eanBanana, false);
		EAN ean =  new EAN(eanBanana);
		assertDoesNotThrow(() -> {
			register.parkReceipt();
		});
	}

	
	@Test
	public void tryToReturnItem(){
		Register register = new Register(1);
		Employee employee = register.addEmployee("Maria Svensson");
		register.LogInEmployee(employee.getId());
		register.createNewReceipt();
		int receiptId = register.getCurrentReceipt().getId();
		Receipt receipt = register.getCurrentReceipt();
		/////item////
		register.addItemCategory("fruits", VATRate.VAT_12, null);
		register.addSupplier("chiquita");
		long eanBanana =  1845678901001l;
		
		register.addItem("banana", 10, "fruits", null, "chiquita", null, eanBanana, false);
		EAN ean =  new EAN(eanBanana);
		register.scanItem(ean);
		register.finishReceipt();
		register.createNewReceipt();
		
		assertDoesNotThrow(() -> {
			register.scanReturnItem(receiptId, ean); 
		});
	}
	
	
	@Test
	public void tryCancelPurchase(){
		Register register = new Register(1);
		Employee employee = register.addEmployee("Maria Svensson");
		register.LogInEmployee(employee.getId());
		register.createNewReceipt();
		int receiptId = register.getCurrentReceipt().getId();
		Receipt receipt = register.getCurrentReceipt();
		/////item////
		register.addItemCategory("fruits", VATRate.VAT_12, null);
		register.addSupplier("chiquita");
		long eanBanana =  1845678901001l;
		
		register.addItem("banana", 10, "fruits", null, "chiquita", null, eanBanana, false);
		EAN ean =  new EAN(eanBanana);
		register.scanItem(ean);
		
		assertDoesNotThrow(() -> {
			register.cancelPurchase();
		});
	}
	
	
	@Test
	public void tryfinishReceipt(){
		Register register = new Register(1);
		Employee employee = register.addEmployee("Maria Svensson");
		register.LogInEmployee(employee.getId());
		register.createNewReceipt();
		int receiptId = register.getCurrentReceipt().getId();
		Receipt receipt = register.getCurrentReceipt();
		/////item////
		register.addItemCategory("fruits", VATRate.VAT_12, null);
		register.addSupplier("chiquita");
		long eanBanana =  1845678901001l;
		
		register.addItem("banana", 10, "fruits", null, "chiquita", null, eanBanana, false);
		EAN ean =  new EAN(eanBanana);
		register.scanItem(ean);
		
		assertDoesNotThrow(() -> {
			register.finishReceipt();
		});
	}
	
	
	
	@Test
	public void tryfinishParkedReceipt(){
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
	public void tryLogOutEmployee(){
		Register register = new Register(1);
		Employee employee = register.addEmployee("Maria Svensson");
		register.LogInEmployee(employee.getId());
		
		assertDoesNotThrow(() -> {
			register.logOutEmployee();
		});
	}
	
	
	@Test
	public void tryAddEmployee(){
		Register register = new Register(1);
		Employee employee = register.addEmployee("Maria Svensson");
		register.LogInEmployee(employee.getId());

		assertEquals(employee, register.getEmployee(employee.getId()));
	}
	
	
	
	
	
}






