package com.lyncode.reflection.input;

public class InputParameter<T> {
    private final T parameter;
    private final boolean used;

    public InputParameter(T parameter, boolean used) {
        this.parameter = parameter;
        this.used = used;
    }

    public T getParameter() {
        return parameter;
    }

    public boolean isUsed() {
        return used;
    }
}
