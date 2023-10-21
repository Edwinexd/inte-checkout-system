import com.agie.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {
    @Test
    public void customerNotNull() {
        Customer c = new Customer(200001010001l);
        assertNotNull(c);
    }

    @Test
    public void customerPnrCantBeLessThanOne() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Customer(-1);
        });
    }

    @Test
    public void customerPnrCantBeMoreThanTwelve() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Customer(2000010100011l);
        });
    }

    @Test
    public void customerPnrLengthIsTwelve() {
        Customer c = new Customer(200001010001l);
        assertEquals(12, String.valueOf(c.getPnr()).length());
    }

    // Leap year tests
    @Test
    public void constructorLeapYearFebruaryTwentyNine() {
        new Customer(200002290001L);
    }

    @Test
    public void constructorNotLeapYearFebruaryTwentyNineThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Customer(190002290001L);
        });
    }

    @Test
    public void constructorLeapYearFebruaryTwentyEight() {
        new Customer(200002280001L);
    }

    @Test
    public void constructorNotLeapYearFebruaryTwentyEight() {
        new Customer(190002280001L);
    }
}
