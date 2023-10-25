import com.agie.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    // A standard customer. No Leap year.
    private Customer getStandardCustomer(){
        return new Customer(1990_01_01_9999L);
    }

    @Test
    public void customerNotNull(){
        assertNotNull(getStandardCustomer());
    }
    @Test
    public void customerIsBornOnTheFirstOfJanuary(){
        assertDoesNotThrow(() -> getStandardCustomer());
    }

    @Test
    public void customerGetPnr(){
        Customer c = getStandardCustomer();
        assertEquals(1990_01_01_9999L, c.getPnr());
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
    public void customerCantBeBornInAFutureYear(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1);
        calendar.add(Calendar.MONTH, 1);
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        String futureDate = "" + year + month + day + 9999;

        assertThrows(IllegalArgumentException.class, () ->{
            new Customer(Long.parseLong(futureDate));
        });
    }

    @Test
    public void customerCantBeBornOnAFutureMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 2);
        final int year = calendar.get(Calendar.YEAR);
        final String month;
        final String day;

        if(calendar.get(Calendar.MONTH) < 10){
            month = "0" + calendar.get(Calendar.MONTH);
        }
        else {
            month = "" + calendar.get(Calendar.MONTH);
        }
        if(calendar.get(Calendar.DAY_OF_MONTH) < 10){
            day = "0" + calendar.get(Calendar.DAY_OF_MONTH);
        }
        else {
            day = "" + calendar.get(Calendar.DAY_OF_MONTH);
        }
        String futureDate = "" + year + month + day + 9999;

        assertThrows(IllegalArgumentException.class, () ->{
            new Customer(Long.parseLong(futureDate));
        });
    }

    @Test
    public void customerCantBeBornOnAFutureDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        final int year = calendar.get(Calendar.YEAR);
        final String month;
        final String day;

        if(calendar.get(Calendar.MONTH) < 10){
            month = "0" + calendar.get(Calendar.MONTH);
        }
        else {
            month = "" + calendar.get(Calendar.MONTH);
        }
        if(calendar.get(Calendar.DAY_OF_MONTH) < 10){
            day = "0" + calendar.get(Calendar.DAY_OF_MONTH);
        }
        else {
            day = "" + calendar.get(Calendar.DAY_OF_MONTH);
        }

        String futureDate = "" + year + month + day + 9999;

        assertThrows(IllegalArgumentException.class, () ->{
            new Customer(Long.parseLong(futureDate));
        });
    }

    @Test
    public void customerBornOnLeapYearFebruary28thDoesntThrowException(){
        new Customer(2000_02_28_9999L);
    }

    @Test
    public void customerBornOnNonLeapYearFebruary28thDoesntThrowException(){
        new Customer(2001_02_28_9999L);
    }

    @Test
    public void customerCantBeBornOnThe29thOfFebruaryOnANonLeapYear(){
        assertThrows(IllegalArgumentException.class, () -> new Customer(2001_02_29_9999L));
    }

    @Test
    public void customerCantBeBornOnThe30thOfFebruaryOnALeapYear(){
        assertThrows(IllegalArgumentException.class, () -> new Customer(2000_02_30_9999L));
    }

    @Test
    public void customerCantBeBornOnThe31stOfFebruaryOnALeapYear(){
        assertThrows(IllegalArgumentException.class, () -> new Customer(2000_02_31_9999L));
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
        assertDoesNotThrow(() -> new Customer(2000_11_30_9999L));
    }

    @Test
    public void customerCanBeBornOnThe31stOfMarch(){
        assertDoesNotThrow(() -> new Customer(2000_03_31_9999L));
    }

    @Test
    public void customerCantBeBornOnThe32ndOfJanuary(){
        assertThrows(IllegalArgumentException.class, () -> new Customer(2000_01_32_9999L));
    }

    @Test
    public void customerCantBeBornOnThe00thOfJanuary(){
        assertThrows(IllegalArgumentException.class, () -> new Customer(2000_01_00_9999L));
    }

    @Test
    public void customerCantBeBornBeforeTheCalenderBeganCounting(){
        assertThrows(IllegalArgumentException.class, () -> new Customer(-1000_01_01_9999L));
    }

    @Test
    public void customerCantBeBornBeforeTheYear1900(){
        assertThrows(IllegalArgumentException.class, () -> new Customer(1899_12_31_9999L));
    }

    @Test
    public void customerAgeIsNotNull(){
        Customer c = new Customer(2005_01_01_9999L);
        assertDoesNotThrow(() -> c.getAge());
    }

    @Test
    public void customerGetAgeIsCorrect(){
        Customer c = new Customer(2005_01_01_9999L);
        assertEquals(18, c.getAge());
    }

    @Test
    public void customerAgeIs18YearsOldToday(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,1);
        final int year = calendar.get(Calendar.YEAR) - 18;
        final String month;
        final String day;

        if(calendar.get(Calendar.MONTH) < 10){
            month = "0" + calendar.get(Calendar.MONTH);
        }else {
            month = "" + calendar.get(Calendar.MONTH);
        }
        if(calendar.get(Calendar.DAY_OF_MONTH) < 10){
            day = "0"+ calendar.get(Calendar.DAY_OF_MONTH);
        }else {
            day ="" + calendar.get(Calendar.DAY_OF_MONTH);
        }
        String futureDate = "" + year + month + day + 9999; // 18 years ago from today.
        Customer c = new Customer(Long.parseLong(futureDate));
        assertEquals(18, c.getAge());
    }

    @Test
    public void customerAgeIs18YearsOldTomorrow(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,1);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        final int year = calendar.get(Calendar.YEAR) - 18;
        final String month;
        final String day;

        if(calendar.get(Calendar.MONTH) < 10){
            month = "0" + calendar.get(Calendar.MONTH);
        }else {
            month = "" + calendar.get(Calendar.MONTH);
        }
        if(calendar.get(Calendar.DAY_OF_MONTH) < 10){
            day = "0"+ calendar.get(Calendar.DAY_OF_MONTH);
        }else {
            day ="" + calendar.get(Calendar.DAY_OF_MONTH);
        }
        String futureDate = "" + year + month + day + 9999; // 18 years ago from tomorrow.
        Customer c = new Customer(Long.parseLong(futureDate));
        assertEquals(17, c.getAge());
    }

    @Test
    public void customerAgeIs18YearsOldYesterday(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,1);

        calendar.add(Calendar.DAY_OF_MONTH, -1);
        final int year = calendar.get(Calendar.YEAR) - 18;
        final String month;
        final String day;

        if(calendar.get(Calendar.MONTH) < 10){
            month = "0" + calendar.get(Calendar.MONTH);
        }else {
            month = "" + calendar.get(Calendar.MONTH);
        }
        if(calendar.get(Calendar.DAY_OF_MONTH) < 10){
            day = "0"+ calendar.get(Calendar.DAY_OF_MONTH);
        }else {
            day ="" + calendar.get(Calendar.DAY_OF_MONTH);
        }
        String futureDate = "" + year + month + day + 9999; // 18 years ago from yesterday
        Customer c = new Customer(Long.parseLong(futureDate));
        assertEquals(18, c.getAge());
    }


}
