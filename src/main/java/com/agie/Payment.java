package com.agie;

import java.math.BigDecimal;
import java.util.HashMap;




public class Payment {
	
	private PaymentType usedPaymentType;
	private Money amountPaid;
	private static final BigDecimal purchaseLimitCash = new BigDecimal(50000);
	private static final BigDecimal purchaseLimitDebitcard = new BigDecimal(50000);
	private static final BigDecimal purchaseLimitCreditcard = new BigDecimal(50000);
	private static final BigDecimal purchaseLimitGiftcard = new BigDecimal(10000);
	private static final BigDecimal purchaseLimitSwish = new BigDecimal(10000);
	private static final BigDecimal refundLimitCash = new BigDecimal(-50000);
	private static final BigDecimal refundLimitDebitcard = new BigDecimal(-50000);
	private static final BigDecimal refundLimitCreditcard = new BigDecimal(-50000);
	private static final BigDecimal refundLimitGiftcard = new BigDecimal(0);
	private static final BigDecimal refundLimitSwish = new BigDecimal(-10000);


	
	public Payment(Money money, PaymentType paymentType) {
		if(money == null ) {
			throw new IllegalArgumentException("Money cannot be null");
		}
		if(paymentType == null ) {
			throw new IllegalArgumentException("Paymenttype cannot be null");
		}
		checkPaymentValidity(money, paymentType);
		processPayment(money, paymentType);
		amountPaid = money;
		usedPaymentType = paymentType;
	}
	
	private void checkPaymentValidity(Money money, PaymentType paymentType) {
		if (money.getAmount().compareTo(BigDecimal.ZERO) == 0) {
			throw new IllegalArgumentException("Payment cannot be zero");
		}
		switch(paymentType) {
		case CASH:
			checkCashPaymentValidity(money);
			break;
		case CREDIT_CARD:
			checkCreditCardPaymentValidity(money);
			break;
		case DEBIT_CARD:
			checkDebitCardPaymentValidity(money);
			break;
		case GIFT_CARD:
			checkGiftCardPaymentValidity(money);
			break;
		case SWISH:
			checkSwishPaymentValidity(money);
			break;
		}
			
	}

	private void checkSwishPaymentValidity(Money money) {
		if(money.getAmount().compareTo(refundLimitSwish)< 0) {
			throw new IllegalArgumentException("Refund has exceeded limit for this payment type");
		}
		if(money.getAmount().compareTo(purchaseLimitSwish) > 0) {
			throw new IllegalArgumentException("Purchase has exceeded highest limit for this payment type");
		}
	}

	private void checkGiftCardPaymentValidity(Money money) {
		if(money.getAmount().compareTo(refundLimitGiftcard) < 0) {
			throw new IllegalArgumentException("Refund cannot be made using this payment type");
		}
		if(money.getAmount().compareTo(purchaseLimitGiftcard) > 0) {
			throw new IllegalArgumentException("Purchase has exceeded highest limit for this payment type");
		}
	}

	private void checkDebitCardPaymentValidity(Money money) {
		if(money.getAmount().compareTo(refundLimitDebitcard)< 0) {
			throw new IllegalArgumentException("Refund has exceeded limit for this payment type");
		}
		if(money.getAmount().compareTo(purchaseLimitDebitcard) > 0) {
			throw new IllegalArgumentException("Purchase has exceeded highest limit for this payment type");
		}
	}

	private void checkCreditCardPaymentValidity(Money money) {
		if(money.getAmount().compareTo(refundLimitCreditcard)< 0) {
			throw new IllegalArgumentException("Refund has exceeded limit for this payment type");
		}
		if(money.getAmount().compareTo(purchaseLimitCreditcard) > 0) {
			throw new IllegalArgumentException("Purchase has exceeded highest limit for this payment type");
		}
	}

	private void checkCashPaymentValidity(Money money) {
		if(money.getAmount().compareTo(refundLimitCash)< 0) {
			throw new IllegalArgumentException("Refund has exceeded limit for this payment type");
		}
		if(money.getAmount().compareTo(purchaseLimitCash) > 0) {
			throw new IllegalArgumentException("Purchase has exceeded highest limit for this payment type");
		}
	}

	private void processPayment(Money money, PaymentType paymentType) {
		boolean paymentWentThrough = true;
		if (!paymentWentThrough) {
			throw new IllegalStateException("Payment could not be made");
		}
	}

	public BigDecimal getAmount(Currency sek) {
		return amountPaid.getAmount();
	}
	
	public Money getMoney() {
		return amountPaid;
	}
	
	public PaymentType getPaymentType() {
		return usedPaymentType;
	}
	
    
}
