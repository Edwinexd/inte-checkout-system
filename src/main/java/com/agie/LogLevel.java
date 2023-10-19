package com.agie;

public enum LogLevel {
    DEBUG,
    INFO,
    WARNING,
    ERROR;

    /*
     * Returns true if this log level includes the given log level.
     */
    public boolean includes(LogLevel logLevel) {
        return this.ordinal() <= logLevel.ordinal();
    }
}
