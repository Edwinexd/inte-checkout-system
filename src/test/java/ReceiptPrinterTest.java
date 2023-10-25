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




