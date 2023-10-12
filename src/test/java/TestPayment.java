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
    public void testRefundNegativeAmountCash() {
    	int validNegativeNumber = -50;
    	Money money = new Money(validNegativeNumber, Currency.SEK);
    	Payment payment = new Payment(money, PaymentType.CASH);
    	assertEquals(money.getAmount(Currency.SEK), payment.getAmount(Currency.SEK));
    }
    
    public void testRefundNegativeAmountCreditCard() {
    	int validNegativeNumber = -50;
    	Money money = new Money(validNegativeNumber, Currency.SEK);
    	Payment payment = new Payment(money, PaymentType.CREDIT_CARD);
    	assertEquals(money.getAmount(Currency.SEK), payment.getAmount(Currency.SEK));
    }
    
    public void testRefundNegativeAmountDebitCard() {
    	int validNegativeNumber = -50;
    	Money money = new Money(validNegativeNumber, Currency.SEK);
    	Payment payment = new Payment(money, PaymentType.DEBIT_CARD);
    	assertEquals(money.getAmount(Currency.SEK), payment.getAmount(Currency.SEK));
    }
    
    public void testRefundNegativeAmountGiftCard() {
    	int validNegativeNumber = -50;
    	Money money = new Money(validNegativeNumber, Currency.SEK);
    	Payment payment = new Payment(money, PaymentType.GIFT_CARD);
    	assertEquals(money.getAmount(Currency.SEK), payment.getAmount(Currency.SEK));
    }
    
    public void testRefundNegativeAmountSwish() {
    	int validNegativeNumber = -50;
    	Money money = new Money(validNegativeNumber, Currency.SEK);
    	Payment payment = new Payment(money, PaymentType.SWISH);
    	assertEquals(money.getAmount(Currency.SEK), payment.getAmount(Currency.SEK));
    }
    
    @Test
    public void testUnderMininimumAmountCash() {
    	final int AmountUnderMinimum = -10001;
    	Money money = new Money(AmountUnderMinimum, Currency.SEK);
    	assertThrows(IllegalArgumentException.class, () -> {
    		new Payment(money, PaymentType.CASH);
    	});
    }
    
    @Test
    public void testUnderMininimumAmountCreditCard() {
    	final int AmountUnderMinimum = -10001;
    	Money money = new Money(AmountUnderMinimum, Currency.SEK);
    	assertThrows(IllegalArgumentException.class, () -> {
    		new Payment(money, PaymentType.CREDIT_CARD);
    	});
    }
    
    @Test
    public void testUnderMininimumAmountDebitCard() {
    	final int AmountUnderMinimum = -10001;
    	Money money = new Money(AmountUnderMinimum, Currency.SEK);
    	assertThrows(IllegalArgumentException.class, () -> {
    		new Payment(money, PaymentType.DEBIT_CARD);
    	});
    }
    
    @Test
    public void testUnderMininimumAmountSwish() {
    	final int AmountUnderMinimum = -10001;
    	Money money = new Money(AmountUnderMinimum, Currency.SEK);
    	assertThrows(IllegalArgumentException.class, () -> {
    		new Payment(money, PaymentType.SWISH);
    	});
    }
    
    @Test
    public void testZeroPayment() {
    	Money money = new Money(0, Currency.SEK);
    	assertThrows(IllegalArgumentException.class, () -> {
    		new Payment(money, PaymentType.CASH); //PaymentType does not matter
    	}); 
    }
    
    @Test
    public void testOverMaxmimumPaymentSwish() {
    	final int AmountOverMaximum = 10001;
    	Money money = new Money(AmountOverMaximum, Currency.SEK);
    	assertThrows(IllegalArgumentException.class, () -> {
    		new Payment(money, PaymentType.CASH);
    	});
    }
    
    @Test
    public void testOverMaxmimumPaymentGiftCard() {
    	final int AmountOverMaximum = 10001;
    	Money money = new Money(AmountOverMaximum, Currency.SEK);
    	assertThrows(IllegalArgumentException.class, () -> {
    		new Payment(money, PaymentType.CASH);
    	});
    }
    
    @Test
    public void testOverMaxmimumPaymentDebitCard() {
    	final int AmountOverMaximum = 10001;
    	Money money = new Money(AmountOverMaximum, Currency.SEK);
    	assertThrows(IllegalArgumentException.class, () -> {
    		new Payment(money, PaymentType.CASH);
    	});
    }
    
    @Test
    public void testOverMaxmimumPaymentCreditCard() {
    	final int AmountOverMaximum = 10001;
    	Money money = new Money(AmountOverMaximum, Currency.SEK);
    	assertThrows(IllegalArgumentException.class, () -> {
    		new Payment(money, PaymentType.CASH);
    	});
    }
    
    @Test
    public void testOverMaxmimumPaymentCASH() {
    	final int AmountOverMaximum = 10001;
    	Money money = new Money(AmountOverMaximum, Currency.SEK);
    	assertThrows(IllegalArgumentException.class, () -> {
    		new Payment(money, PaymentType.CASH);
    	});
    }
    
    @Test
    public void testPurchasePositiveAmountGiftCard() {
    	int validPositiveNumber = 50;
    	Money money = new Money(validPositiveNumber, Currency.SEK);
    	Payment payment = new Payment(money, PaymentType.GIFT_CARD);
    	assertEquals(money.getAmount(Currency.SEK), payment.getAmount(Currency.SEK));
    }
    
    @Test
    public void testPurchasePositiveAmountSwish() {
    	int validPositiveNumber = 50;
    	Money money = new Money(validPositiveNumber, Currency.SEK);
    	Payment payment = new Payment(money, PaymentType.SWISH);
    	assertEquals(money.getAmount(Currency.SEK), payment.getAmount(Currency.SEK));
    }
    @Test
    public void testPurchasePositiveAmountDebitCard() {
    	int validPositiveNumber = 50;
    	Money money = new Money(validPositiveNumber, Currency.SEK);
    	Payment payment = new Payment(money, PaymentType.DEBIT_CARD);
    	assertEquals(money.getAmount(Currency.SEK), payment.getAmount(Currency.SEK));
    }
    @Test
    public void testPurchasePositiveAmountCreditCard() {
    	int validPositiveNumber = 50;
    	Money money = new Money(validPositiveNumber, Currency.SEK);
    	Payment payment = new Payment(money, PaymentType.CREDIT_CARD);
    	assertEquals(money.getAmount(Currency.SEK), payment.getAmount(Currency.SEK));
    }
    @Test
    public void testPurchasePositiveAmountCash() {
    	int validPositiveNumber = 50;
    	Money money = new Money(validPositiveNumber, Currency.SEK);
    	Payment payment = new Payment(money, PaymentType.CASH);
    	assertEquals(money.getAmount(Currency.SEK), payment.getAmount(Currency.SEK));
    }

    
    
    
    
}
