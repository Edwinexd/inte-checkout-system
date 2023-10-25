import com.agie.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class LoggerTest {
    
    private FileWriter getMockedFileWriter(){
        FileWriter mockedFileWriter = mock(FileWriter.class);
        return mockedFileWriter;
    }

    private void writesLineOfEachLevel(Logger logger) throws LoggingException {
        logger.log(LogLevel.DEBUG, "debug message");
        logger.log(LogLevel.INFO, "info message");
        logger.log(LogLevel.WARNING, "warning message");
        logger.log(LogLevel.ERROR, "error message");
    }


    @Test
    public void constructorLogLevelNullThrowsException(){
        FileWriter mockedFileWriter = getMockedFileWriter();
        assertThrows(IllegalArgumentException.class, () -> {
            new Logger(null, mockedFileWriter);
        });
    }

    @Test
    public void constructorWriterProvidedNullThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Logger(LogLevel.DEBUG, null);
        });
    }

    @Test
    public void logLogLevelNullThrowsException(){
        FileWriter mockedFileWriter = getMockedFileWriter();
        Logger logger = new Logger(LogLevel.DEBUG, mockedFileWriter);
        assertThrows(IllegalArgumentException.class, () -> {
            logger.log(null, "message");
        });
    }

    @Test
    public void logMessageNullThrowsException(){
        FileWriter mockedFileWriter = getMockedFileWriter();
        Logger logger = new Logger(LogLevel.DEBUG, mockedFileWriter);
        assertThrows(IllegalArgumentException.class, () -> {
            logger.log(LogLevel.DEBUG, null);
        });
    }

    // This test is commented/disabled as it always will create a file
    // ideas on how to test this better are welcome! / Edwin
    // @Test
    // public void noWriterProvidedCreatesWithDefaultWriter(){
    //     assertDoesNotThrow(() -> {
    //         new Logger(LogLevel.DEBUG);
    //     });
    // }

    @Test
    public void loggerWithDebugLevelLogsAll() throws LoggingException {
        FileWriter mockedFileWriter = getMockedFileWriter();
        Logger logger = new Logger(LogLevel.DEBUG, mockedFileWriter);
        writeLineOfEachLevel(logger);
        verify(mockedFileWriter, times(4)).push(anyString());
    }

    @Test
    public void loggerWithInfoLevelLogsInfoAndAbove() throws LoggingException {
        FileWriter mockedFileWriter = getMockedFileWriter();
        Logger logger = new Logger(LogLevel.INFO, mockedFileWriter);
        writeLineOfEachLevel(logger);
        verify(mockedFileWriter, times(3)).push(anyString());
    }

    @Test
    public void loggerWithWarningLevelLogsWarningAndAbove() throws LoggingException {
        FileWriter mockedFileWriter = getMockedFileWriter();
        Logger logger = new Logger(LogLevel.WARNING, mockedFileWriter);
        writeLineOfEachLevel(logger);
        verify(mockedFileWriter, times(2)).push(anyString());
    }

    @Test
    public void loggerWithErrorLevelLogsErrorOnly() throws LoggingException {
        FileWriter mockedFileWriter = getMockedFileWriter();
        Logger logger = new Logger(LogLevel.ERROR, mockedFileWriter);
        writeLineOfEachLevel(logger);
        verify(mockedFileWriter, times(1)).push(anyString());
    }



}
