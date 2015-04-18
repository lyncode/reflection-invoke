package com.lyncode.reflection.integration.example;

import com.lyncode.reflection.input.InputParameterValueResolver;

public class TestArgumentValueResolver implements InputParameterValueResolver<TestArgument> {
    @Override
    public Object resolve(TestArgument value) {
        return value.getValue();
    }
}
