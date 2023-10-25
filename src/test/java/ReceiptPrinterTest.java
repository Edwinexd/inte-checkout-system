import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
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

	// @Mock annotation is used to create the mock object to be injected
	// Interfacet Receiptprinter;

	@Mock
	ReceiptPrinter receiptPrinter;

	// CalculatorService calcService;

	// @InjectMocks annotation is used to create and inject the mock object
	// Register klassen

	@InjectMocks
	Register register = new Register(1);

	// MathApplication mathApplication = new MathApplication();

	@Test
	public void printActiveRecieptTest() {

		// add the behavior of calc service to add two numbers
		//when(receiptPrinter.printReceipt(register.getCurrentReceipt())).thenReturn(true);
		when(receiptPrinter.printActiveReceipt()).thenReturn(true);

		// when(calcService.add(10.0,20.0)).thenReturn(30.00);
		// public double add(double input1, double input2);
		// test the add functionality
		assertEquals(register.printReceipt(), true);

		// Assert.assertEquals(mathApplication.add(10.0, 20.0),30.0,0);
	}
	
	
	@Test
	public void paperLeftTest() {
		
		// add the behavior of calc service to add two numbers
		//when(receiptPrinter.printReceipt(register.getCurrentReceipt())).thenReturn(true);
		when(receiptPrinter.getPaperLeft()).thenReturn(20);

		// when(calcService.add(10.0,20.0)).thenReturn(30.00);
		// public double add(double input1, double input2);
		// test the add functionality
		assertEquals(register.getHowManyPaperLeft(), 20);

		// Assert.assertEquals(mathApplication.add(10.0, 20.0),30.0,0);
	}
	
	@Test
	public void printDailyReceiptsTest() {
		
		// add the behavior of calc service to add two numbers
		//when(receiptPrinter.printReceipt(register.getCurrentReceipt())).thenReturn(true);
		when(receiptPrinter.printDailyReceipts()).thenReturn(true);

		// when(calcService.add(10.0,20.0)).thenReturn(30.00);
		// public double add(double input1, double input2);
		// test the add functionality
		assertEquals(register.printDailyReceipts(), true);

		// Assert.assertEquals(mathApplication.add(10.0, 20.0),30.0,0);
	}

}




