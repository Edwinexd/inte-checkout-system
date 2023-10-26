/*
Checkout System - A checkout system with focus on testing - group assignment for the INTE course at Stockholm University Autumn 2023
Copyright (C) 2023 Gusten Berghäll, Ida Laaksonen, Adrian Martvall, Edwin Sundberg 

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/
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

    private void writeLineOfEachLevel(Logger logger) throws LoggingException {
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
