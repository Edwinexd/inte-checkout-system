import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.agie.ItemRow;
import com.agie.Money;
import com.agie.Supplier;
import com.agie.VATRate;
import com.agie.ItemCategory;
import com.agie.Currency;
import com.agie.Item;

public class ItemRowTest {
    private static final Item VALID_WEIGHT_ITEM = new Item("Test", new Money(1, Currency.SEK),
        new ItemCategory("Fruit", VATRate.VAT_12, null),
        null,
        new Supplier("Test"),
        null,
        true
    );
    
    private static final Item VALID_ITEM = new Item("Test", new Money(2, Currency.SEK),
        new ItemCategory("Wheat", VATRate.VAT_12, null),
        null,
        new Supplier("Test"),
        null,
        false
    );

    private static final Item VALID_ITEM_25_VAT = new Item("Test", new Money(10, Currency.SEK),
        new ItemCategory("Car", VATRate.VAT_25, null),
        null,
        new Supplier("Test"),
        null,
        false
    );

    private static final double VALID_QUANTITY = 1;

    @Test
    public void constructorTest() {
        new ItemRow(VALID_WEIGHT_ITEM, VALID_QUANTITY);
        new ItemRow(VALID_ITEM, VALID_QUANTITY);
    }

    @Test
    public void constructorNullThrows () {
        assertThrows(IllegalArgumentException.class, () -> {
            new ItemRow(null, VALID_QUANTITY);
        });
    }

    @Test
    public void quantityZeroThrows () {
        assertThrows(IllegalArgumentException.class, () -> {
            new ItemRow(VALID_WEIGHT_ITEM, 0);
        });
    }

    @Test
    public void getTotalWeightBasedTest() {
        ItemRow itemRow = new ItemRow(VALID_WEIGHT_ITEM, VALID_QUANTITY);
        Money total = itemRow.getTotal().total;
        assertEquals(new Money(1, Currency.SEK), total);
    }

    @Test
    public void getTotalNonWeightBasedTest() {
        ItemRow itemRow = new ItemRow(VALID_ITEM, VALID_QUANTITY);
        Money total = itemRow.getTotal().total;
        assertEquals(new Money(2, Currency.SEK), total);
    }

    @Test
    public void getTotalVATNonWeightBasedTest() {
        ItemRow itemRow = new ItemRow(VALID_ITEM_25_VAT, VALID_QUANTITY);
        Money vat = itemRow.getTotal().totalWithVat;
        assertEquals(new Money(12.5, Currency.SEK), vat);
    }

    @Test
    public void getTotalVATWeightBasedTest() {
        ItemRow itemRow = new ItemRow(VALID_WEIGHT_ITEM, VALID_QUANTITY);
        Money vat = itemRow.getTotal().totalWithVat;
        assertEquals(new Money(1.12, Currency.SEK), vat);
    }

    @Test
    public void addQuantityNewValueZeroThrows () {
        ItemRow itemRow = new ItemRow(VALID_WEIGHT_ITEM, -1);
        assertThrows(IllegalArgumentException.class, () -> {
            itemRow.addQuantity(1);
        });
    }

    @Test
    public void addQuantityTest() {
        ItemRow itemRow = new ItemRow(VALID_WEIGHT_ITEM, VALID_QUANTITY);
        ItemRow newRow = itemRow.addQuantity(1d);
        assertEquals(2d, newRow.getQuantity());
    }

    @Test
    public void subtractQuantityNewValueZeroThrows () {
        ItemRow itemRow = new ItemRow(VALID_WEIGHT_ITEM, 1);
        assertThrows(IllegalArgumentException.class, () -> {
            itemRow.subtractQuantity(1);
        });
    }

    @Test
    public void subtractQuantityTest() {
        ItemRow itemRow = new ItemRow(VALID_WEIGHT_ITEM, 2d);
        ItemRow newRow = itemRow.subtractQuantity(1d);
        assertEquals(1d, newRow.getQuantity());
    }

    @Test
    public void toStringTest() {
        ItemRow itemRow = new ItemRow(VALID_WEIGHT_ITEM, VALID_QUANTITY);
        assertEquals("1.000 - Test - 1.00 kr / kg", itemRow.toString());
    }
}
