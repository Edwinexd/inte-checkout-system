package com.agie;

public class Supplier {
    private final String name;

    public Supplier (String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        this.name = name;
    }

    public String getName () {
        return name;
    }
}
