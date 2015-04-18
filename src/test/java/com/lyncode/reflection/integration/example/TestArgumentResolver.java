package com.lyncode.reflection.integration.example;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.lyncode.reflection.input.InputParameter;
import com.lyncode.reflection.input.InputParameterResolver;
import com.lyncode.reflection.model.java.JavaMethodArgument;

import java.util.List;

public class TestArgumentResolver implements InputParameterResolver<TestArgument> {
    @Override
    public Optional<TestArgument> resolve(JavaMethodArgument methodArgument, List<InputParameter<TestArgument>> inputParameters) {
        int position = methodArgument.position();
        if (position >= inputParameters.size()) return Optional.absent();
        return Optional.fromNullable(inputParameters.get(position)).transform(new Function<InputParameter<TestArgument>, TestArgument>() {
            @Override
            public TestArgument apply(InputParameter<TestArgument> input) {
                return input.getParameter();
            }
        });
    }
}
