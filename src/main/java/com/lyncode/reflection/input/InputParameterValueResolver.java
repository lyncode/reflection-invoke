package com.lyncode.reflection.input;

public interface InputParameterValueResolver<InputParameterType> {
    Object resolve (InputParameterType value);
}
