import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.agie.Main;
import com.agie.Money;
import com.agie.Payment;
import com.agie.Currency;
import com.agie.PaymentType;

public class TestPayment {

    @Test
    public void testRefundNegativeAmount() {
    	int validNegativeNumber = -50;
    	Money money = new Money(validNegativeNumber, Currency.SEK)
    	Payment payment = new Payment(money, PaymentType.Cash);
    	assertEquals(money.getAmountOfMoney(Currency.SEK), Payment.getAmountOfMoney(Currency.SEK));
    }
    
    @Test
    public void testUnderMininimumAmount() {
    	final int AmountUnderMinimum = -10001;
    	Money money = new Money(AmountUnderMinimum, Currency.SEK)
    	assertThrows(IllegalArgumentException, new Payment(money))
    }
    
    @Test
    public void testZeroPayment() {
    	Money money = new Money(0, Currency.SEK)
    	assertThrows(IllegalArgumentException, new Payment(money))
    }
    
    @Test
    public void testOverMaxmimumPayment() {
    	final int AmountOverMaximum = 10001;
    	Money money = new Money(AmountOverMaximum, Currency.SEK)
    	assertThrows(IllegalArgumentException, new Payment(money))
    }
    
    @Test
    public void testPurchasePositiveAmount() {
    	int validPositiveNumber = 50;
    	Money money = new Money(validPositiveNumber, Currency.SEK)
    	Payment payment = new Payment(money);
    	assertEquals(money.getAmountOfMoney(Currency.SEK), Payment.getAmountOfMoney(Currency.SEK));
    }
    
    
    
    
    
}
