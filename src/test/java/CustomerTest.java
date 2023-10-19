import com.agie.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {
    @Test
    public void customerNotNull(){
        Customer c = new Customer(0001010001);
        assertNotNull(c);
    }

    @Test
    public void customerPnrNotNull(){
        Customer c = new Customer(0001010001);
        assertNotNull(c.getPnr());
    }

    @Test
    public void customerPnrCantBeLessThanOne(){
        assertThrows(IllegalArgumentException.class, () ->{
            new Customer(-1);
        });
    }

    @Test
    public void customerPnrCantBeMoreThanTen(){
        assertThrows(IllegalArgumentException.class, () ->{
            new Customer(00010100011);
        });
    }

    @Test
    public void customerPnrLengthIsTen(){
        Customer c = new Customer(0001010001);
        assertEquals(10, String.valueOf(c.getPnr()).length());
    }

    /*
    Must do the thing where you bypass the access modifier to test private methods
    @Test
    public void isLeapYearReturnTrue(){
        Customer c = new Customer(200002029999L);
        assertEquals(true, c.isYearALeapYear(2000));
    }

     */

}
