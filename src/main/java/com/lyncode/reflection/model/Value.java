package com.lyncode.reflection.model;

public class Value {
    private final Object value;

    public Value(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }
}
