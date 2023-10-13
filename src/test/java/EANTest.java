import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.agie.EAN;

public class EANTest {
    public static final long VALID_EAN = 1234567890128L;
    public static final long INVALID_EAN_LENGTH = 123456789012L;
    public static final long INVALID_EAN_CHECK_DIGIT = 1234567890123L;

    @Test
    public void constructorInvalidNumberThrows () {
        assertThrows(IllegalArgumentException.class, () -> {
            new EAN(INVALID_EAN_LENGTH);
        });
    }

    @Test
    public void constructorInvalidCheckDigitThrows () {
        assertThrows(IllegalArgumentException.class, () -> {
            new EAN(INVALID_EAN_CHECK_DIGIT);
        });
    }

    @Test
    public void constructorNegativeThrows () {
        assertThrows(IllegalArgumentException.class, () -> {
            new EAN(-1234567890128L); // Casted to string would yield a 13 digit number
        });
    }

    @Test
    public void constructorTest() {
        new EAN(VALID_EAN);
    }

    @Test
    public void equalsTest() {
        EAN ean1 = new EAN(VALID_EAN);
        EAN ean2 = new EAN(VALID_EAN);
        assertEquals(ean1, ean2);
    }

    @Test
    public void toStringTest() {
        EAN ean = new EAN(VALID_EAN);
        assertEquals(Long.toString(VALID_EAN), ean.toString());
    }

}
