package com.lyncode.reflection.integration.example;

import com.google.common.base.Optional;
import com.lyncode.reflection.input.InputParameterResolver;
import com.lyncode.reflection.input.InputParameterResolverContext;
import com.lyncode.reflection.model.java.JavaMethodArgument;

public class TestArgumentResolver implements InputParameterResolver<TestArgument> {
    @Override
    public Optional<TestArgument> resolve(JavaMethodArgument argument, InputParameterResolverContext<TestArgument> context) {
        int position = argument.position();
        if (position >= context.size()) return Optional.absent();
        context.markAsUsed(position);
        return Optional.fromNullable(context.value(position));
    }
}
