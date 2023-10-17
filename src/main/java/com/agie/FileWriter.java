package com.agie;

import java.io.IOException;

public class FileWriter implements LogHandler {
    private java.io.FileWriter fileWriter;

    public FileWriter(String path, boolean append) throws IOException {
        if (path == null) {
            throw new IllegalArgumentException("path cannot be null");
        }
        this.fileWriter = new java.io.FileWriter(path, append);
    }

    public FileWriter(java.io.FileWriter fileWriter) {
        if (fileWriter == null) {
            throw new IllegalArgumentException("fileWriter cannot be null");
        }
        this.fileWriter = fileWriter;
    }

    private void write(String message) throws IOException {
        if (message == null) {
            throw new IllegalArgumentException("message cannot be null");
        }
        fileWriter.write(message);
    }

    @Override
    public void push(String message) throws IOException {
        if (message == null) {
            throw new IllegalArgumentException("message cannot be null");
        }
        this.write(message + "\n");
    }
}




