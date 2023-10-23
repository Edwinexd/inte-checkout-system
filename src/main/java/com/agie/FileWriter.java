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




