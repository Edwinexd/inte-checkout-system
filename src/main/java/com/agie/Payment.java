package com.agie;

import java.math.BigDecimal;




public class Payment {
	
	private PaymentType usedPaymentType;
	private Money amountPaid;
	
	public Payment(Money money, PaymentType paymentType) {
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
		if(money.getAmount().compareTo(new BigDecimal(-10000))< 0) {
			throw new IllegalArgumentException("Refund has exceeded limit for this payment type");
		}
		if(money.getAmount().compareTo(new BigDecimal(10000)) > 0) {
			throw new IllegalArgumentException("Purchase has exceeded highest limit for this payment type");
		}
	}

	private void checkGiftCardPaymentValidity(Money money) {
		if(money.getAmount().compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("Refund cannot be made using this payment type");
		}
		if(money.getAmount().compareTo(new BigDecimal(5000)) > 0) {
			throw new IllegalArgumentException("Purchase has exceeded highest limit for this payment type");
		}
	}

	private void checkDebitCardPaymentValidity(Money money) {
		if(money.getAmount().compareTo(new BigDecimal(-50000))< 0) {
			throw new IllegalArgumentException("Refund has exceeded limit for this payment type");
		}
		if(money.getAmount().compareTo(new BigDecimal(50000)) > 0) {
			throw new IllegalArgumentException("Purchase has exceeded highest limit for this payment type");
		}
	}

	private void checkCreditCardPaymentValidity(Money money) {
		if(money.getAmount().compareTo(new BigDecimal(-50000))< 0) {
			throw new IllegalArgumentException("Refund has exceeded limit for this payment type");
		}
		if(money.getAmount().compareTo(new BigDecimal(50000)) > 0) {
			throw new IllegalArgumentException("Purchase has exceeded highest limit for this payment type");
		}
	}

	private void checkCashPaymentValidity(Money money) {
		if(money.getAmount().compareTo(new BigDecimal(-10000))< 0) {
			throw new IllegalArgumentException("Refund has exceeded limit for this payment type");
		}
		if(money.getAmount().compareTo(new BigDecimal(10000)) > 0) {
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
