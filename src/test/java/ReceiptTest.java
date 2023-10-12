import com.agie.ItemRow;
import com.agie.Receipt;
import jdk.vm.ci.code.Register;
import org.junit.jupiter.api.Test;

import com.agie.Main;

import static org.junit.jupiter.api.Assertions.*;

public class ReceiptTest {
    @Test
    public void createReceipt(){
        Receipt r = new Receipt(1);
        assertNotNull(r);
    }

    @Test
    public void receiptDateNotNull(){
        Receipt r = new Receipt(1);
        assertNotNull(r.getDate());
    }

    @Test
    public void receiptReturnsMoney(){
        Receipt r = new Receipt(1);
        assertEquals(r.getTotalExVat(),0);
    }

    @Test
    public void receiptIdCantBeLessThanOne(){
        assertThrows(IllegalArgumentException.class, () ->{
            Receipt r = new Receipt(-1);
        });
    }

    @Test
    public void receiptAddItem(){
        Receipt r = new Receipt(1);
        ItemRow r = new ItemRow();

        r.addRow(ItemRow r);
    }




}
