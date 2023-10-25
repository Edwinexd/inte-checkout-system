package com.agie;

import java.math.BigDecimal;
import java.util.HashMap;

import javax.management.RuntimeErrorException;

import com.agie.PaymentTerminal.PaymentStatus;

public class Payment {

	private PaymentType chosenPaymentType;
	private Money amount;
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
	private PaymentTerminal paymentTerminal;
	private boolean processed;

	public Payment(Money money, PaymentType paymentType) {
		if (money == null) {
			throw new IllegalArgumentException("Money cannot be null");
		}
		if (paymentType == null) {
			throw new IllegalArgumentException("Paymenttype cannot be null");
		}
		checkPaymentValidity(money, paymentType);
		amount = money;
		chosenPaymentType = paymentType;
	}

	public void startPaymentTerminal() {
		if (!paymentTerminal.isTurnedOn()) {
			paymentTerminal.start();
		} else {
			throw new IllegalStateException("Payment terminal already turned on");
		}
	}

	public void processPayment() {
		if (!paymentTerminal.isTurnedOn()) {
			throw new IllegalStateException("Payment terminal is not turned on");
		}
		if (!paymentTerminal.isReady()) {
			PaymentStatus paymentStatus = paymentTerminal.getPaymentStatus();
			switch (paymentStatus) {
			case INITIATED:
				throw new IllegalStateException("Another payment is already initiated");
			case PROCESSING:
				throw new IllegalStateException("Another payment is processing");
			}
		}
		Money money = this.amount;
		PaymentType paymentType = this.chosenPaymentType;
		boolean paymentWentThrough = false;
		switch (paymentType) {
		case CASH:
			paymentWentThrough = true;
			break;
		case CREDIT_CARD:
			paymentWentThrough = paymentTerminal.makeCreditCardTransaction(money);
			if (!paymentWentThrough) {
				paymentTerminal.retryLastOperation();
			}
			break;
		case DEBIT_CARD:
			paymentWentThrough = paymentTerminal.makeDebitCardTransaction(money);
			if (!paymentWentThrough) {
				paymentTerminal.retryLastOperation();
			}
			break;
		case GIFT_CARD:
			paymentWentThrough = true;
			break;
		case SWISH:
			paymentWentThrough = true;
			break;
		}
		if (!paymentWentThrough) {
			throw new IllegalStateException("Payment could not be made");
		}
		processed = true;
	}

	public void cancelPayment() {
		switch (paymentTerminal.getPaymentStatus()) {
		case PROCESSING:
			throw new IllegalStateException("Payment is already being processed");
		case INITIATED:
			paymentTerminal.abortOperation();
		}
	}

	public void restartPaymentTerminal() {
		if (paymentTerminal.getPaymentStatus() == PaymentStatus.FINISHED) {
			paymentTerminal.restart();
		} else {
			throw new IllegalStateException("Payment in progress");
		}
	}

	private void checkPaymentValidity(Money money, PaymentType paymentType) {
		if (money.getAmount().compareTo(BigDecimal.ZERO) == 0) {
			throw new IllegalArgumentException("Payment cannot be zero");
		}
		switch (paymentType) {
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
		if (money.getAmount().compareTo(refundLimitSwish) < 0) {
			throw new IllegalArgumentException("Refund has exceeded limit for this payment type");
		}
		if (money.getAmount().compareTo(purchaseLimitSwish) > 0) {
			throw new IllegalArgumentException("Purchase has exceeded highest limit for this payment type");
		}
	}

	private void checkGiftCardPaymentValidity(Money money) {
		if (money.getAmount().compareTo(refundLimitGiftcard) < 0) {
			throw new IllegalArgumentException("Refund cannot be made using this payment type");
		}
		if (money.getAmount().compareTo(purchaseLimitGiftcard) > 0) {
			throw new IllegalArgumentException("Purchase has exceeded highest limit for this payment type");
		}
	}

	private void checkDebitCardPaymentValidity(Money money) {
		if (money.getAmount().compareTo(refundLimitDebitcard) < 0) {
			throw new IllegalArgumentException("Refund has exceeded limit for this payment type");
		}
		if (money.getAmount().compareTo(purchaseLimitDebitcard) > 0) {
			throw new IllegalArgumentException("Purchase has exceeded highest limit for this payment type");
		}
	}

	private void checkCreditCardPaymentValidity(Money money) {
		if (money.getAmount().compareTo(refundLimitCreditcard) < 0) {
			throw new IllegalArgumentException("Refund has exceeded limit for this payment type");
		}
		if (money.getAmount().compareTo(purchaseLimitCreditcard) > 0) {
			throw new IllegalArgumentException("Purchase has exceeded highest limit for this payment type");
		}
	}

	private void checkCashPaymentValidity(Money money) {
		if (money.getAmount().compareTo(refundLimitCash) < 0) {
			throw new IllegalArgumentException("Refund has exceeded limit for this payment type");
		}
		if (money.getAmount().compareTo(purchaseLimitCash) > 0) {
			throw new IllegalArgumentException("Purchase has exceeded highest limit for this payment type");
		}
	}

	public BigDecimal getAmount(Currency sek) {
		return amount.getAmount();
	}

	public Money getMoney() {
		return amount;
	}

	public PaymentType getPaymentType() {
		return chosenPaymentType;
	}

	public boolean isProcessed() {
		return processed;
	}

}
