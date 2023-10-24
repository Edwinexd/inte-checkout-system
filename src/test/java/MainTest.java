import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.agie.Main;

public class MainTest {

    @Test
    public void testAdd() {
        assertEquals(3, Main.add(1, 2));
    }
}
