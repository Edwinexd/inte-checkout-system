
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
import org.junit.jupiter.api.BeforeEach;
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
import com.agie.PaymentTerminal.PaymentStatus;
import com.agie.PaymentType;
import com.agie.Receipt;
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
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.lang.reflect.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PaymentTerminalTest {

	Money validMoney = new Money(50, Currency.SEK);
	Money zeroMoney = new Money(BigDecimal.ZERO, Currency.SEK);
	Money validRefundMoney = new Money(-50, Currency.SEK);

	@Mock
	PaymentTerminal paymentTerminal;

	@InjectMocks
	Payment payment = new Payment(validMoney, PaymentType.CREDIT_CARD);
	Field amountField;
	Field paymentTypeField;
	
	@BeforeEach
	public void setValues() throws NoSuchFieldException, SecurityException {
		amountField = Payment.class.getDeclaredField("amount");
		amountField.setAccessible(true);
		paymentTypeField = Payment.class.getDeclaredField("chosenPaymentType");
		paymentTypeField.setAccessible(true);
		when(paymentTerminal.getPaymentStatus()).thenReturn(PaymentStatus.FINISHED);
		when(paymentTerminal.isTurnedOn()).thenReturn(true);
	}

	@Test
	public void testPurchaseCreditCard() throws IllegalArgumentException, IllegalAccessException {

		amountField.set(payment, validMoney);
		paymentTypeField.set(payment, PaymentType.CREDIT_CARD);
		
		when(paymentTerminal.makeCreditCardTransaction(payment.getMoney())).thenReturn(true);
		payment.processPayment();
		assertEquals(true, payment.isProcessed());

	}

	@Test
	public void testPurchaseDebitCard() throws IllegalArgumentException, IllegalAccessException{
 
		amountField.set(payment, validMoney);
		paymentTypeField.set(payment, PaymentType.DEBIT_CARD);

		when(paymentTerminal.makeDebitCardTransaction(payment.getMoney())).thenReturn(true);
		payment.processPayment();
		assertEquals(true, payment.isProcessed());

	}

	@Test
	public void testZeroPurchaseDebitCard() throws IllegalArgumentException, IllegalAccessException {

		amountField.set(payment, zeroMoney);
		paymentTypeField.set(payment, PaymentType.DEBIT_CARD);

		when(paymentTerminal.makeDebitCardTransaction(payment.getMoney())).thenThrow(IllegalArgumentException.class);
		assertThrows(IllegalArgumentException.class, () -> {
			payment.processPayment();
		});
	}

	@Test
	public void testZeroPurchaseCreditCard() throws IllegalArgumentException, IllegalAccessException {

		amountField.set(payment, zeroMoney);
		paymentTypeField.set(payment, PaymentType.CREDIT_CARD);

		when(paymentTerminal.makeCreditCardTransaction(payment.getMoney())).thenThrow(IllegalArgumentException.class);
		assertThrows(IllegalArgumentException.class, () -> {
			payment.processPayment();
		});
	}
	
	@Test
	public void testRefundDebitCard() throws IllegalArgumentException, IllegalAccessException {

		amountField.set(payment, validRefundMoney);
		paymentTypeField.set(payment, PaymentType.DEBIT_CARD);

		when(paymentTerminal.makeDebitCardTransaction(payment.getMoney())).thenReturn(true);
		payment.processPayment();
		assertEquals(true, payment.isProcessed());

	}
	
	@Test
	public void testRefundCreditCard() throws IllegalArgumentException, IllegalAccessException {

		amountField.set(payment, validRefundMoney);
		paymentTypeField.set(payment, PaymentType.CREDIT_CARD);

		when(paymentTerminal.makeCreditCardTransaction(payment.getMoney())).thenReturn(true);
		payment.processPayment();
		assertEquals(true, payment.isProcessed());

	}
	
	@Test
	public void testStartPaymentTerminal() {
		when(paymentTerminal.isTurnedOn()).thenReturn(false);
		assertDoesNotThrow(() -> {
			payment.startPaymentTerminal();
		});
	}
	
	@Test
	public void testStartPaymentTerminalWhenTurnedOn() {
		when(paymentTerminal.isTurnedOn()).thenReturn(true);
		assertThrows(IllegalStateException.class, () -> {
			payment.startPaymentTerminal();
		});
	}
	
	@Test
	public void testPaymentDuringInitiatedPayment() throws IllegalArgumentException, IllegalAccessException {

		amountField.set(payment, validMoney);
		paymentTypeField.set(payment, PaymentType.CREDIT_CARD);
		when(paymentTerminal.getPaymentStatus()).thenReturn(PaymentStatus.INITIATED);
		assertThrows(IllegalStateException.class, () -> {
			payment.processPayment();
		});
	}
	
	@Test
	public void testPaymentDuringInProgressPayment() throws IllegalArgumentException, IllegalAccessException {

		amountField.set(payment, validMoney);
		paymentTypeField.set(payment, PaymentType.CREDIT_CARD);
		when(paymentTerminal.getPaymentStatus()).thenReturn(PaymentStatus.PROCESSING);
		assertThrows(IllegalStateException.class, () -> {
			payment.processPayment();
		});
	}
	
	@Test
	public void testPaymentWhenTerminalOff() throws IllegalArgumentException, IllegalAccessException {

		amountField.set(payment, validMoney);
		paymentTypeField.set(payment, PaymentType.CREDIT_CARD);
		when(paymentTerminal.isTurnedOn()).thenReturn(false);
		assertThrows(IllegalStateException.class, () -> {
			payment.processPayment();
		});
	}
	
	@Test
	public void testPurchaseCreditCardRetry() throws IllegalArgumentException, IllegalAccessException {

		amountField.set(payment, validMoney);
		paymentTypeField.set(payment, PaymentType.CREDIT_CARD);
		
		when(paymentTerminal.makeCreditCardTransaction(payment.getMoney())).thenReturn(false);
		assertThrows(IllegalStateException.class, () -> {
			payment.processPayment();
		});

	}
	
	@Test
	public void testPurchaseDebitCardRetry() throws IllegalArgumentException, IllegalAccessException {

		amountField.set(payment, validMoney);
		paymentTypeField.set(payment, PaymentType.DEBIT_CARD);
		
		when(paymentTerminal.makeDebitCardTransaction(payment.getMoney())).thenReturn(false);
		assertThrows(IllegalStateException.class, () -> { 
			payment.processPayment();
		});

	}
	
	@Test
	public void testPurchaseCash() throws IllegalArgumentException, IllegalAccessException {

		amountField.set(payment, validMoney);
		paymentTypeField.set(payment, PaymentType.CASH);
	
		payment.processPayment();
		assertEquals(true, payment.isProcessed());

	}
	
	@Test
	public void testPurchaseGiftCard() throws IllegalArgumentException, IllegalAccessException {

		amountField.set(payment, validMoney);
		paymentTypeField.set(payment, PaymentType.GIFT_CARD);
	
		payment.processPayment();
		assertEquals(true, payment.isProcessed());

	}
	
	@Test
	public void testPurchaseSwish() throws IllegalArgumentException, IllegalAccessException {

		amountField.set(payment, validMoney);
		paymentTypeField.set(payment, PaymentType.SWISH);
	
		payment.processPayment();
		assertEquals(true, payment.isProcessed());

	}
	
	@Test
	public void testCancelProcessingPurchase() throws IllegalArgumentException, IllegalAccessException {
	
		when(paymentTerminal.getPaymentStatus()).thenReturn(PaymentStatus.PROCESSING);
		assertThrows(IllegalStateException.class, () -> {
			payment.cancelPayment();
		});
	}
	
	@Test
	public void testCancelInitiatedPurchase() throws IllegalArgumentException, IllegalAccessException {
	
		when(paymentTerminal.getPaymentStatus()).thenReturn(PaymentStatus.INITIATED);
		assertDoesNotThrow(() -> {
			payment.cancelPayment();
		});
	}
	
	@Test
	public void testRestartTerminal() throws IllegalArgumentException, IllegalAccessException {
		when(paymentTerminal.getPaymentStatus()).thenReturn(PaymentStatus.FINISHED);
		assertDoesNotThrow(() -> {
			payment.restartPaymentTerminal();
		});
	}
	
	@Test
	public void testRestartTerminalDuringPurchase() throws IllegalArgumentException, IllegalAccessException {
	
		when(paymentTerminal.getPaymentStatus()).thenReturn(PaymentStatus.INITIATED);
		assertThrows(IllegalStateException.class, () -> {
			payment.restartPaymentTerminal();
		});
	}
}