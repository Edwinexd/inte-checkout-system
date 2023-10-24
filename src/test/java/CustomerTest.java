import com.agie.Customer;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Month;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    // A standard customer. No Leap year.
    private Customer getStandardCustomer(){
        return new Customer(1990_01_01_9999L);
    }

    @Test
    public void customerNotNull(){
        Customer c = getStandardCustomer();
        assertNotNull(c);
    }

    @Test
    public void customerGetPnr(){
        Customer c = getStandardCustomer();

        assertEquals(1990_01_01_9999L, c.getPnr());
    }

    @Test
    public void customerPnrCantBeLessThanOne(){
        assertThrows(IllegalArgumentException.class, () -> new Customer(-1L));
    }

    @Test
    public void customerPnrCantBeMoreThanTwelveInLength(){
        assertThrows(IllegalArgumentException.class, () ->{
            new Customer(1990_01_01_9999_0L); //13 digits long
        });
    }
    @Test
    public void customerPnrCantBeLessThan12InLength(){
        assertThrows(IllegalArgumentException.class, () ->{
            new Customer(1990_01_01_999L); // 11 digits long
        });
    }

    @Test
    public void customerPnrCantBe12InLengthAndNegative(){
        assertThrows(IllegalArgumentException.class, () ->{
            new Customer(-1990_01_01_999L);
        });
    }

    @Test
    public void customerPnrCantBeNegative(){
        assertThrows(IllegalArgumentException.class, () -> new Customer(-1L));
    }

    @Test
    public void customerPnrLengthIsTwelve(){
        Customer c = new Customer(199001019999L);
        assertEquals(12, String.valueOf(c.getPnr()).length());
    }

    // We have to bypass the access modifier (private) for isYearALeapYear to test it.
    // This is done through the help of java.lang.reflect.Method
    @Test
    public void isLeapYearReturnTrue() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = Customer.class.getDeclaredMethod("isYearALeapYear", int.class);
        method.setAccessible(true);

        Customer c = new Customer(2000_01_01_9999L);
        assertEquals(true, method.invoke(c, 2000));
    }

    @Test
    public void isLeapYearReturnFalse() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = Customer.class.getDeclaredMethod("isYearALeapYear", int.class);
        method.setAccessible(true);

        Customer c = new Customer(1990_01_01_9999L);
        assertEquals(false, method.invoke(c, 2001));
    }

    @Test
    public void customerCantBeBornInTheFuture(){
        assertThrows(IllegalArgumentException.class, () ->{
            new Customer(9999_01_01_9999L); // Assumes you're not running the program post year-9999.
        });
    }

    @Test
    public void customerCantBeBornOnThe31stOfFebruary(){
        assertThrows(IllegalArgumentException.class, () -> new Customer(2000_02_31_9999L));
    }

    @Test
    public void customerCantBeBornOnThe29thOfFebruaryOnANonLeapYear(){
        assertThrows(IllegalArgumentException.class, () -> new Customer(2001_02_29_9999L));
    }

    @Test
    public void customerCanBeBornOnThe29thOfFebruaryOnALeapYear(){
        assertNotNull(new Customer(2000_02_29_9999L));
    }

    @Test
    public void customerCantBeBornOnThe30thOfFebruaryOnALeapYear(){
        assertThrows(IllegalArgumentException.class, () -> new Customer(2000_02_30_9999L));
    }

    @Test
    public void customerCantBeBornOnThe31stOfApril(){
        assertThrows(IllegalArgumentException.class, () -> new Customer(2000_04_31_9999L));
    }

    @Test
    public void customerCantBeBornOnThe31stOfJune(){
        assertThrows(IllegalArgumentException.class, () -> new Customer(2000_06_31_9999L));
    }

    @Test
    public void customerCantBeBornOnThe31stOfSeptember(){
        assertThrows(IllegalArgumentException.class, () -> new Customer(2000_09_31_9999L));
    }

    @Test
    public void customerCantBeBornOnThe31stOfNovember(){
        assertThrows(IllegalArgumentException.class, () -> new Customer(2000_11_31_9999L));
    }

    @Test
    public void customerCanBeBornOnThe30thOfNovember(){
        Customer c = new Customer(2000_11_30_9999L);
        assertEquals(2000_11_30_9999L, c.getPnr());
    }

    @Test
    public void customerCantBeBornOnThe32ndOfJanuary(){
        assertThrows(IllegalArgumentException.class, () -> new Customer(2000_01_32_9999L));
    }

    @Test
    public void customerCantBeBornOnThe00thOfJanuary(){
        assertThrows(IllegalArgumentException.class, () -> new Customer(2000_01_00_9999L));
    }

    @Test // 0th instead of 00th
    public void customerCantBeBornOnThe0thOfJanuary(){
        assertThrows(IllegalArgumentException.class, () -> new Customer(2000_01_0_9999L));
    }

    @Test
    public void customerCantBeBornBeforeTheCalenderBeganCounting(){
        assertThrows(IllegalArgumentException.class, () -> new Customer(-1000_01_01_9999L));
    }

    @Test
    public void customerCantBeBornBeforeTheYear1900(){
        assertThrows(IllegalArgumentException.class, () -> new Customer(-1899_01_01_9999L));
    }

    @Test
    public void customerAgeIsNotNull(){
        Customer c = new Customer(2005_01_01_9999L);
        assertNotNull(c.getAge());
    }

    @Test
    public void customerAgeIs18(){
        Customer c = new Customer(2005_01_01_9999L);
        assertEquals(18, c.getAge());
    }

    @Test
    public void customerAgeIs17InCurrentYearButWrongMonth(){
        Customer c = new Customer(2005_12_01_9999L);
        assertEquals(17, c.getAge());
    }

    @Test
    public void customerAgeIs17InCurrentYearAndCurrentMonthButWrongDay(){
        Customer c = new Customer(2005_10_31_9999L);
        assertEquals(17, c.getAge());
    }


}
