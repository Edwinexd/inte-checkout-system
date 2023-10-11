import static org.junit.jupiter.api.Assertions.assertEquals;

import com.agie.Receipt;
import jdk.vm.ci.code.Register;
import org.junit.jupiter.api.Test;

import com.agie.Main;

public class ReceiptTest {
    @Test
    public void createReceipt(){
        Receipt r = new Receipt(1);
    }
}
