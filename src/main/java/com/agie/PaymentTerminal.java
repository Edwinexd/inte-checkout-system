package com.agie;

public interface PaymentTerminal {
	
	public boolean makeDebitCardTransaction(Money amount) throws IllegalStateException, IllegalArgumentException;
	public boolean makeCreditCardTransaction(Money amount) throws IllegalStateException, IllegalArgumentException;
	public void start() throws IllegalStateException;
	public void restart() throws IllegalStateException;
	public boolean abortOperation() throws IllegalStateException;
	public PaymentStatus getPaymentStatus() throws IllegalStateException;
	public boolean retryLastOperation() throws IllegalStateException;
	public boolean isTurnedOn();
	public boolean isReady();
	
	
	enum PaymentStatus {
		INITIATED,
		PROCESSING,
		FINISHED
	}

}
