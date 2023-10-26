import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.agie.Currency;
import com.agie.Main;
import com.agie.Money;
import com.agie.Payment;

import net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Validator;

public class MoneyTest {

	@Test
	public void testNullMoney() {
		assertThrows(IllegalArgumentException.class, () -> {
			Money money = new Money(null, Currency.SEK);
		});
	}

	@Test
	public void testNullCurrency() {
		int validPositiveNumber = 50;
		assertThrows(IllegalArgumentException.class, () -> {
			Money money = new Money(validPositiveNumber, null);
		});
	}

	@Test
	public void testGetPositiveAmount() {
		int validPositiveNumber = 50;
		Money money = new Money(validPositiveNumber, Currency.SEK);
		assertTrue(money.getAmount(Currency.SEK).compareTo(new BigDecimal(validPositiveNumber)) == 0);
	}
	
	@Test
	public void testGetPositiveAmountNoCurrency() {
		int validPositiveNumber = 50;
		Money money = new Money(validPositiveNumber, Currency.SEK);
		assertTrue(money.getAmount().compareTo(new BigDecimal(validPositiveNumber)) == 0);
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
		assertTrue(money1.add(money2).getAmount(Currency.SEK).compareTo(new BigDecimal(validPositiveNumber * 2)) == 0);
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
		assertTrue(money1.multiply(money2).getAmount(Currency.SEK)
				.compareTo(new BigDecimal(validPositiveNumber).multiply(new BigDecimal(validPositiveNumber))) == 0);
	}

	@Test
	public void testMultiplyMoneyInt() {
		int validPositiveNumber = 5;
		Money money = new Money(validPositiveNumber, Currency.SEK);
		assertEquals(new Money(validPositiveNumber * 5, Currency.SEK), money.multiply(validPositiveNumber));
	}

	@Test
	public void testMultiplyMoneyDouble() {
		int validPositiveNumber = 5;
		double validPositiveDouble = 1.5;
		Money money = new Money(validPositiveNumber, Currency.SEK);
		assertEquals(new Money(validPositiveNumber * validPositiveDouble, Currency.SEK),
				money.multiply(validPositiveDouble));
	}

	@Test
	public void testMultiplyMoneyBigDecimal() {
		int validPositiveNumber = 5;
		BigDecimal validPositiveBigDecimal = new BigDecimal(1.5);
		Money money = new Money(validPositiveNumber, Currency.SEK);
		assertEquals(new Money(new BigDecimal(validPositiveNumber).multiply(validPositiveBigDecimal), Currency.SEK),
				money.multiply(validPositiveBigDecimal));
	}

	@Test
	public void testDivideMoney() {
		int validPositiveNumber = 5;
		Money money1 = new Money(validPositiveNumber, Currency.SEK);
		Money money2 = new Money(validPositiveNumber, Currency.SEK);
		assertTrue(money1.divide(money2).getAmount(Currency.SEK)
				.compareTo(new BigDecimal(validPositiveNumber).divide(new BigDecimal(validPositiveNumber))) == 0);
	}

	@Test
	public void testDivideByZeroThrows() {
		int validPositiveNumber = 5;
		Money money1 = new Money(validPositiveNumber, Currency.SEK);
		Money money2 = new Money(0, Currency.SEK);
		assertThrows(ArithmeticException.class, () -> {
			money1.divide(money2);
		});
	}

	@Test
	public void testDivideMoneyInt() {
		int validPositiveNumber = 5;
		Money money = new Money(validPositiveNumber, Currency.SEK);
		assertEquals(new Money(validPositiveNumber / 5, Currency.SEK), money.divide(validPositiveNumber));
	}

	@Test
	public void testDivideMoneyDouble() {
		int validPositiveNumber = 5;
		double validPositiveDouble = 2.5;
		Money money = new Money(validPositiveNumber, Currency.SEK);
		assertEquals(new Money(validPositiveNumber / validPositiveDouble, Currency.SEK),
				money.divide(validPositiveDouble));
	}

	@Test
	public void testDivideMoneyBigDecimal() {
		int validPositiveNumber = 5;
		BigDecimal validPositiveBigDecimal = new BigDecimal(1.5);
		Money money = new Money(validPositiveNumber, Currency.SEK);
		Money expected = new Money((double) validPositiveNumber / validPositiveBigDecimal.doubleValue(), Currency.SEK);
		assertEquals(expected, money.divide(validPositiveBigDecimal));
	}

	@Test
	public void equalsTest() {
		Money money1 = new Money(1, Currency.SEK);
		Money money2 = new Money(1, Currency.SEK);
		assertEquals(money1, money2);
	}

	@Test
	public void notEqualsTest() {
		Money money1 = new Money(1, Currency.SEK);
		String testString = "hello";
		assertNotEquals(money1, testString);
	}

	@Test
	public void equalsDifferentCurrencyFalse() {
		Money money1 = new Money(1, Currency.SEK);
		Money money2 = new Money(1, Currency.EUR);
		assertTrue(!money1.equals(money2));
	}

	@Test
	public void equalsDifferentAmountFalse() {
		Money money1 = new Money(1, Currency.SEK);
		Money money2 = new Money(2, Currency.SEK);
		assertTrue(!money1.equals(money2));
	}

	@Test
	public void toStringTest() {
		Money money = new Money(1, Currency.SEK);
		assertEquals("1.00", money.toString());
	}
}
