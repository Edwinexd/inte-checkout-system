import com.agie.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.FileNotFoundException;
import java.io.IOException;


public class FileWriterTest { 
    private java.io.FileWriter getMockedFileWriter(){
        java.io.FileWriter mockedFileWriter = mock(java.io.FileWriter.class);
        return mockedFileWriter;
    }
    
    @Test
    public void constructorPathNullThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new FileWriter(null, true);
        });
    }

    @Test
    public void constructorPathEmptyThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new FileWriter("", true);
        });
    }

    @Test
    public void constructorPathIllegalCharactersThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new FileWriter("path\\with\\illegal\\characters", true);
        });
    }

    @Test
    public void constructorPathValidTest() throws IOException {
        // This test will pass if the path is valid but taking into account that the file may not exist
        try {
            new FileWriter("/dev/null", true);
        } catch (FileNotFoundException e) {

        }
    }

    @Test
    public void constructorWriterProvidedNullThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new FileWriter(null);
        });
    }

    @Test
    public void pushMessageNullThrowsException() {
        java.io.FileWriter mockedFileWriter = getMockedFileWriter();
        FileWriter fileWriter = new FileWriter(mockedFileWriter);
        assertThrows(IllegalArgumentException.class, () -> {
            fileWriter.push(null);
        });
    }

    @Test
    public void pushMessageEmptyWritesBlankLine() throws LoggingException, IOException {
        java.io.FileWriter mockedFileWriter = getMockedFileWriter();
        FileWriter fileWriter = new FileWriter(mockedFileWriter);
        fileWriter.push("");
        verify(mockedFileWriter, times(1)).write("\n");
    }

    @Test
    public void pushMessageWritesMessage() throws LoggingException, IOException {
        java.io.FileWriter mockedFileWriter = getMockedFileWriter();
        FileWriter fileWriter = new FileWriter(mockedFileWriter);
        fileWriter.push("message");
        verify(mockedFileWriter, times(1)).write("message\n");
    }

    @Test
    public void pushIOErrorThrowsLoggingException() throws LoggingException, IOException {
        java.io.FileWriter mockedFileWriter = getMockedFileWriter();
        doThrow(IOException.class).when(mockedFileWriter).write("message\n");
        FileWriter fileWriter = new FileWriter(mockedFileWriter);
        assertThrows(LoggingException.class, () -> {
            fileWriter.push("message");
        });
    }
}
