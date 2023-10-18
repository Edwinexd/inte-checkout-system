package com.agie;

import java.io.IOException;

public class Logger {
    private LogHandler handler;
    private LogLevel logLevel;

    private static String getStandardLogPath() {
        return String.format("log_%s.txt", System.currentTimeMillis());
    }

    public Logger(LogLevel logLevel) throws IOException {
        this(logLevel, new FileWriter(getStandardLogPath(), true));        
    }

    public Logger(LogLevel logLevel, LogHandler handler) {
        if (handler == null) {
            throw new IllegalArgumentException("handler cannot be null");
        }
        if (logLevel == null) {
            throw new IllegalArgumentException("logLevel cannot be null");
        }
        this.handler = handler;
        this.logLevel = logLevel;
    }


    public void log(LogLevel logLevel, String message) throws LoggingException {
        if (logLevel == null) {
            throw new IllegalArgumentException("logLevel cannot be null");
        }
        if (message == null) {
            throw new IllegalArgumentException("message cannot be null");
        }
        if (!this.logLevel.includes(logLevel)) {
            return;
        }
        String logLevelString = String.format("%s: %s", logLevel.toString(), message);
        handler.push(logLevelString);
    }
}
