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
