import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.agie.Main;
import com.agie.Money;
import com.agie.Payment;
import com.agie.Currency;
import com.agie.PaymentType;

public class PaymentTest {
	

    @Test
    public void testRefundNegativeAmountCash() {
    	int validNegativeNumber = -50;
    	Money money = new Money(validNegativeNumber, Currency.SEK);
    	Payment payment = new Payment(money, PaymentType.CASH);
    	assertEquals(money.getAmount(Currency.SEK), payment.getAmount(Currency.SEK));
    }
    
	@Test
    public void testRefundNegativeAmountCreditCard() {
    	int validNegativeNumber = -50;
    	Money money = new Money(validNegativeNumber, Currency.SEK);
    	Payment payment = new Payment(money, PaymentType.CREDIT_CARD);
    	assertEquals(money.getAmount(Currency.SEK), payment.getAmount(Currency.SEK));
    }
    
	@Test
    public void testRefundNegativeAmountDebitCard() {
    	int validNegativeNumber = -50;
    	Money money = new Money(validNegativeNumber, Currency.SEK);
    	Payment payment = new Payment(money, PaymentType.DEBIT_CARD);
    	assertEquals(money.getAmount(Currency.SEK), payment.getAmount(Currency.SEK));
    }
    
	@Test
    public void testRefundNegativeAmountGiftCard() {
    	int validNegativeNumber = -50;
    	Money money = new Money(validNegativeNumber, Currency.SEK);
    	assertThrows(IllegalArgumentException.class, () -> {
			new Payment(money, PaymentType.GIFT_CARD);
		});
    }
    
	@Test
    public void testRefundNegativeAmountSwish() {
    	int validNegativeNumber = -50;
    	Money money = new Money(validNegativeNumber, Currency.SEK);
    	Payment payment = new Payment(money, PaymentType.SWISH);
    	assertEquals(money.getAmount(Currency.SEK), payment.getAmount(Currency.SEK));
    }
    
    @Test
    public void testUnderMininimumAmountCash() {
    	final int AmountUnderMinimum = -50001;
    	Money money = new Money(AmountUnderMinimum, Currency.SEK);
    	assertThrows(IllegalArgumentException.class, () -> {
    		new Payment(money, PaymentType.CASH);
    	});
    }
    
    @Test
    public void testUnderMininimumAmountCreditCard() {
    	final int AmountUnderMinimum = -50001;
    	Money money = new Money(AmountUnderMinimum, Currency.SEK);
    	assertThrows(IllegalArgumentException.class, () -> {
    		new Payment(money, PaymentType.CREDIT_CARD);
    	});
    }
    
    @Test
    public void testUnderMininimumAmountDebitCard() {
    	final int AmountUnderMinimum = -50001;
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
    		new Payment(money, PaymentType.SWISH);
    	});
    }
    
    @Test
    public void testOverMaxmimumPaymentGiftCard() {
    	final int AmountOverMaximum = 10001;
    	Money money = new Money(AmountOverMaximum, Currency.SEK);
    	assertThrows(IllegalArgumentException.class, () -> {
    		new Payment(money, PaymentType.GIFT_CARD);
    	});
    }
    
    @Test
    public void testOverMaxmimumPaymentDebitCard() {
    	final int AmountOverMaximum = 50001;
    	Money money = new Money(AmountOverMaximum, Currency.SEK);
    	assertThrows(IllegalArgumentException.class, () -> {
    		new Payment(money, PaymentType.DEBIT_CARD);
    	});
    }
    
    @Test
    public void testOverMaxmimumPaymentCreditCard() {
    	final int AmountOverMaximum = 50001;
    	Money money = new Money(AmountOverMaximum, Currency.SEK);
    	assertThrows(IllegalArgumentException.class, () -> {
    		new Payment(money, PaymentType.CREDIT_CARD);
    	});
    }
    
    @Test
    public void testOverMaxmimumPaymentCASH() {
    	final int AmountOverMaximum = 50001;
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
    
    //CHATGPT ASSISTED TESTCASES:
    
    //Verify that the getMoney() method returns the expected Money object.
    @Test
    public void testGetMoney() {
        Money money = new Money(50, Currency.SEK);
        Payment payment = new Payment(money, PaymentType.CASH);
        assertEquals(money, payment.getMoney());
    }
    
    //Verify that the getPaymentType() method returns the expected payment type.
    @Test
    public void testGetPaymentType() {
        Money money = new Money(50, Currency.SEK);
        PaymentType paymentType = PaymentType.CASH;
        Payment payment = new Payment(money, paymentType);
        assertEquals(paymentType, payment.getPaymentType());
    }

    //Ensure that an IllegalArgumentException is thrown when attempting to create a payment with null money.
    @Test
    public void testCreatePaymentWithNullMoney() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment(null, PaymentType.CASH);
        });
    }
    
    //Ensure that an IllegalArgumentException is thrown when attempting to create a payment with a null payment type.
    @Test
    public void testCreatePaymentWithNullPaymentType() {
        Money money = new Money(50, Currency.SEK);
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment(money, null);
        });
    }
    
    //Verify that a purchase with the lowest valid amount for a gift card (0) is accepted.
    @Test
    public void testLowestValidPurchaseForGiftCard() {
        Money money = new Money(1, Currency.SEK);
        Payment payment = new Payment(money, PaymentType.GIFT_CARD);
        assertEquals(money.getAmount(Currency.SEK), payment.getAmount(Currency.SEK));
    }
    
    //Verify that a purchase with the highest valid amount for cash is accepted.
    @Test
    public void testHighestValidPurchaseForCash() {
        Money money = new Money(50000, Currency.SEK);
        Payment payment = new Payment(money, PaymentType.CASH);
        assertEquals(money.getAmount(Currency.SEK), payment.getAmount(Currency.SEK));
    }
    
    //Verify that a refund with the highest valid amount for a debit card is accepted.
    @Test
    public void testHighestValidRefundForDebitCard() {
        Money money = new Money(-50000, Currency.SEK);
        Payment payment = new Payment(money, PaymentType.DEBIT_CARD);
        assertEquals(money.getAmount(Currency.SEK), payment.getAmount(Currency.SEK));
    }
    
    //Verify that a purchase with the highest valid amount for a credit card is accepted.
    @Test
    public void testHighestValidPurchaseForCreditCard() {
        Money money = new Money(50000, Currency.SEK);
        Payment payment = new Payment(money, PaymentType.CREDIT_CARD);
        assertEquals(money.getAmount(Currency.SEK), payment.getAmount(Currency.SEK));
    }
    
    //Verify that a refund with the highest valid amount for Swish is accepted.
    @Test
    public void testHighestValidRefundForSwish() {
        Money money = new Money(-10000, Currency.SEK);
        Payment payment = new Payment(money, PaymentType.SWISH);
        assertEquals(money.getAmount(Currency.SEK), payment.getAmount(Currency.SEK));
    }
    
    //Verify that a purchase with the lowest valid amount for cash is accepted.
    @Test
    public void testLowestValidPurchaseForCash() {
        Money money = new Money(1, Currency.SEK);
        Payment payment = new Payment(money, PaymentType.CASH);
        assertEquals(money.getAmount(Currency.SEK), payment.getAmount(Currency.SEK));
    }
    
}
