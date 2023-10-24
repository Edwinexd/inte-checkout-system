package com.agie;

public interface PaymentTerminal {
	
	public boolean makeDebitCardTransaction(Money amount) throws IllegalStateException, IllegalArgumentException;
	public boolean makeCreditCardTransaction(Money amount) throws IllegalStateException, IllegalArgumentException;
	public void start() throws IllegalStateException;
	public void restart() throws IllegalStateException;
	public boolean abortOperation() throws IllegalStateException;
	public paymentStatus getPaymentStatus() throws IllegalStateException;
	public boolean retryLastOperation() throws IllegalStateException;
	
	enum paymentStatus {
		INITIATED,
		PROCESSING,
		FINISHED
	}

}
