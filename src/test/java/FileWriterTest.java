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
