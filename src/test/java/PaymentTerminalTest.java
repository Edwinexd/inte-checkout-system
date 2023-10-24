
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Formatter.BigDecimalLayoutForm;
import java.util.HashMap;

import javax.naming.Context;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import com.agie.AgeLimit;
import com.agie.Currency;
import com.agie.EAN;
import com.agie.Employee;
import com.agie.Item;
import com.agie.Money;
import com.agie.Payment;
import com.agie.PaymentTerminal;
import com.agie.PaymentType;
import com.agie.Receipt;
import com.agie.ReceiptPrinter;
import com.agie.Register;
import com.agie.VATRate;

import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.when;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import java.lang.reflect.*;


@ExtendWith(MockitoExtension.class)
public class PaymentTerminalTest {
	
	
	Money validMoney = new Money(50, Currency.SEK);
	Money zeroMoney = new Money(BigDecimal.ZERO, Currency.SEK);
	
	@Mock
	PaymentTerminal paymentTerminal;
	
	@InjectMocks
	Payment paymentMockPurchaseCreditCard = new Payment(validMoney, PaymentType.CREDIT_CARD);
	@InjectMocks
	Payment paymentMockPurchaseDebitCard = new Payment(validMoney, PaymentType.DEBIT_CARD);
	@InjectMocks
	Payment paymentZeroPurchaseCreditCard = new Payment(validMoney, PaymentType.CREDIT_CARD);
	@InjectMocks
	Payment paymentZeroPurchaseDebitCard = new Payment(validMoney, PaymentType.DEBIT_CARD);
	
	@Test
	public void testMockPurchaseCreditCard() {
		
		when(paymentTerminal.makeCreditCardTransaction(validMoney)).thenReturn(true);
		paymentMockPurchaseCreditCard.processPayment();
		assertEquals(true, paymentMockPurchaseCreditCard.isProcessed());
		
	}
	
	@Test
	public void testMockPurchaseDebitCard() {
		
		when(paymentTerminal.makeDebitCardTransaction(validMoney)).thenReturn(true);
		paymentMockPurchaseDebitCard.processPayment();
		assertEquals(true, paymentMockPurchaseDebitCard.isProcessed());
	}
	
	@Test
	public void testMockZeroPurchaseCreditCard() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field amountField = Payment.class.getDeclaredField("amount");
		amountField.setAccessible(true);
		amountField.set(paymentZeroPurchaseCreditCard, new Money(BigDecimal.ZERO, Currency.SEK));
		when(paymentTerminal.makeCreditCardTransaction(paymentZeroPurchaseCreditCard.getMoney())).thenThrow(IllegalArgumentException.class);
		assertThrows(IllegalArgumentException.class, () -> {
			paymentZeroPurchaseCreditCard.processPayment();
		});	}
	
	@Test
	public void testMockZeroPurchaseDebitCard() {
		
		when(paymentTerminal.makeDebitCardTransaction(validMoney)).thenThrow(IllegalArgumentException.class);
		paymentZeroPurchaseDebitCard.processPayment();
		assertEquals(true, paymentMockPurchaseDebitCard.isProcessed());
	}
	
}