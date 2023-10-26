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
package com.agie;

import java.io.IOException;
import java.util.regex.Pattern;

public class FileWriter implements LogHandler {
    private static final Pattern pathPattern = Pattern.compile("^[\\w./]+$");
    private java.io.FileWriter fileWriter;

    public FileWriter(String path, boolean append) throws IOException {
        if (path == null) {
            throw new IllegalArgumentException("path cannot be null");
        }
        if (!pathPattern.matcher(path).matches()) {
            throw new IllegalArgumentException("path contains illegal characters");
        }

        this.fileWriter = new java.io.FileWriter(path, append);
    }

    public FileWriter(java.io.FileWriter fileWriter) {
        if (fileWriter == null) {
            throw new IllegalArgumentException("fileWriter cannot be null");
        }
        this.fileWriter = fileWriter;
    }

    private void write(String message) throws LoggingException {
        try {
            fileWriter.write(message);
        } catch (IOException e) {
            throw new LoggingException("Error writing to file", e);
        }
    }

    @Override
    public void push(String message) throws LoggingException {
        if (message == null) {
            throw new IllegalArgumentException("message cannot be null");
        }
        this.write(message + "\n");
    }
}




