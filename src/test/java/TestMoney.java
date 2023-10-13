import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.agie.Currency;
import com.agie.Main;
import com.agie.Money;
import com.agie.Payment;

public class TestMoney {

	@Test
    public void testGetPositiveAmount() {
    	int validPositiveNumber = 50;
    	Money money = new Money(validPositiveNumber, Currency.SEK);
    	assertTrue(money.getAmount(Currency.SEK).compareTo(new BigDecimal(validPositiveNumber)) == 0);
    }
	
	@Test
    public void testGetNegativeAmount() {
    	int validPositiveNumber = -50;
    	Money money = new Money(validPositiveNumber, Currency.SEK);
    	assertTrue(money.getAmount(Currency.SEK).compareTo(new BigDecimal(validPositiveNumber)) == 0);
    }
	
	@Test
    public void testGetZeroAmount() {
    	int validPositiveNumber = 0;
    	Money money = new Money(validPositiveNumber, Currency.SEK);
    	assertTrue(money.getAmount(Currency.SEK).compareTo(new BigDecimal(validPositiveNumber)) == 0);
    }
    
	@Test
    public void testAddMoney() {
    	int validPositiveNumber = 50;
    	Money money1 = new Money(validPositiveNumber, Currency.SEK);
    	Money money2 = new Money(validPositiveNumber, Currency.SEK);
    	assertTrue(money1.add(money2).getAmount(Currency.SEK).compareTo(new BigDecimal(validPositiveNumber*2)) == 0);
    }
    
    @Test
    public void testSubtractMoney() {
    	int validPositiveNumber = 50;
    	Money money1 = new Money(validPositiveNumber, Currency.SEK);
    	Money money2 = new Money(validPositiveNumber, Currency.SEK);
    	assertTrue(money1.subtract(money2).getAmount(Currency.SEK).compareTo(new BigDecimal(0)) == 0);
    }
    
    @Test
    public void testMultiplyMoney() {
    	int validPositiveNumber = 5;
    	Money money1 = new Money(validPositiveNumber, Currency.SEK);
    	Money money2 = new Money(validPositiveNumber, Currency.SEK);
    	assertTrue(money1.multiply(money2).getAmount(Currency.SEK).compareTo(new BigDecimal(validPositiveNumber).multiply(new BigDecimal(validPositiveNumber))) == 0);
    }
	
    @Test
    public void testDivideMoney() {
    	int validPositiveNumber = 5;
    	Money money1 = new Money(validPositiveNumber, Currency.SEK);
    	Money money2 = new Money(validPositiveNumber, Currency.SEK);
    	assertTrue(money1.divide(money2).getAmount(Currency.SEK).compareTo(new BigDecimal(validPositiveNumber).divide(new BigDecimal(validPositiveNumber))) == 0);
    }
	
    
    
}
