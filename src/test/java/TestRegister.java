import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.agie.Register;

public class TestRegister {

	
	@Test
	public void doEmployeeHaveRightIdNumber(){
		Register register = new Register();
		assertEquals(employee.getId(), 95503);
	}
	
	@Test
	public void loggInEmployee(){
		Register register = new Register(1);
		int id = 95503;
		register.LogInEmployee(95503);
		assertEquals(register.getloggedInEmployeeId(), 95503);
	}
	
	
	@Test
	public void tryScanningItem(){
		Register register = new Register(1);
		Item item = new Item();
		assertThrows(IllegalStateException.class, () -> {
			register.scanItem(item);
		});
	}
	
	@Test
	public void removeItemFromReceipt(){
		Register register = new Register(1);
		Receipt reciept = new Reciept();
		Item item = new Item();
		assertThrows(IllegalStateException.class, () -> {
			Receipt.remove(item); 
		});
	}
	
	@Test
	public void tryParkeraKöp(){
		Register register = new Register(1);
		Purchase purchase = new Purchase();
		assertThrows(IllegalStateException.class, () -> {
			register.parkeraPurchase(purchase);
		});
	}
	
	
	@Test
	public void getOldReceipt(){
		Register register = new Register(1);
		Receipt receipt = new Receipt();
		assertThrows(IllegalStateException.class, () -> {
			register.getOldReceipt(register.getReceiptId(0055)); 
		});
	}
	
	
	@Test
	public void seeIfItemIsOnOldReceipt(){
		Register register = new Register(1);
		Receipt receipt = new Receipt();
		Item apple = new Item(apple, 10, 7888);
		assertThrows(IllegalStateException.class, () -> {
			register.getOldReceipt(register.getReceiptId(2244).getItem(apple)); 
		});
	}
	
	
	@Test
	public void returnItem(){
		Register register = new Register(1);
		assertThrows(IllegalStateException.class, () -> {
			register.returnItem(0055); 
		});
	}
	
	
	@Test
	public void returnProductAndAddIntoLagerSaldo(){
		Register register = new Register(1);
		assertThrows(IllegalStateException.class, () -> {
			register.returnItem(0055); 
			register.getItemInSaldo(0055);
		});
	}
	
	
	
	
	@Test
	public void isItemInLagerSaldo(){
		Register register = new Register(1);
		assertThrows(IllegalStateException.class, () -> {
			register.getItemInSaldo(0055);
		});
	}

	@Test
	public void isItemTypeInLagerSaldo(){
		Register register = new Register(1);
		Item apple = new Item(apple, 10, 7888);
		assertThrows(IllegalStateException.class, () -> {
			register.isItemTypeInSaldo(apple);
		});
	}
	
	
	@Test
	public void removeItemFromLagerSaldo(){
		Register register = new Register(1);
		Item item = new Item();
		assertThrows(IllegalStateException.class, () -> {
			lagerSaldo.remove(item); 
		});
	}
	
	
	
	
}






