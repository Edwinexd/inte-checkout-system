package com.agie;

import java.io.IOException;

public interface LogHandler {
    public void push(String message) throws IOException;
}
