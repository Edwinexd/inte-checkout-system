package com.agie;

import java.io.IOException;

public class LoggingException extends Exception {
    public LoggingException(String message, IOException e) {
        super(message, e);
    }
}
