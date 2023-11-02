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
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import com.agie.AgeLimit;
import com.agie.Currency;
import com.agie.EAN;
import com.agie.Employee;
import com.agie.Item;
import com.agie.Money;
import com.agie.Receipt;
import com.agie.ReceiptPrinter;
import com.agie.Register;
import com.agie.VATRate;

//@RunWith(MockitoJUnitRunner.class)

@ExtendWith(MockitoExtension.class)
public class ReceiptPrinterTest {

	@Mock
	ReceiptPrinter receiptPrinter;

	@InjectMocks
	Register register = new Register(1);

	@Test
	public void printActiveRecieptTest() {

		when(receiptPrinter.printActiveReceipt()).thenReturn(true);

		assertEquals(register.printReceipt(), true);

	}
	
	
	@Test
	public void paperLeftTest() {

		when(receiptPrinter.getPaperLeft()).thenReturn(20);

		assertEquals(register.getHowManyPaperLeft(), 20);

	}
	
	@Test
	public void printDailyReceiptsTest() {
		
		when(receiptPrinter.printDailyReceipts()).thenReturn(true);

		assertEquals(register.printDailyReceipts(), true);

	}

}




