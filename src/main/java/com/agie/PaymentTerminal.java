/*
Checkout System - A checkout system with focus on testing - group assignment for the INTE course at Stockholm University Autumn 2023
Copyright (C) 2023 Gusten Berghäll, Ida Laaksonen, Adrian Martvall, Edwin Sundberg 

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/
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
