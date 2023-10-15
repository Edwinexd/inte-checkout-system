
//import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.agie.Currency;
import com.agie.EAN;
import com.agie.Item;
import com.agie.ItemCategory;
import com.agie.Money;
import com.agie.Register;
import com.agie.Supplier;
import com.agie.VATRate;

public class RegisterTestsMore {

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
		assertEquals("banana - 10.00 kr", register.printItems());
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

}
