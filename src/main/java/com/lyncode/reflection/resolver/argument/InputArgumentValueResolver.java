package com.lyncode.reflection.resolver.argument;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.lyncode.reflection.input.InputParameterResolverContext;
import com.lyncode.reflection.model.Value;
import com.lyncode.reflection.model.java.JavaMethodArgument;

public class InputArgumentValueResolver<InputParameterType> {
    private final InputArgumentResolverConfiguration<InputParameterType> configuration;

    public InputArgumentValueResolver(InputArgumentResolverConfiguration<InputParameterType> configuration) {
        this.configuration = configuration;
    }

    public Optional<Value> resolve(JavaMethodArgument methodArgument, InputParameterResolverContext<InputParameterType> context) {
        return configuration.getInputParameterResolver()
                .resolve(methodArgument, context)
                .transform(getValue())
                .transform(convert(methodArgument.type()))
                .or(Optional.<Optional<Value>>absent())
                .or(Optional.<Value>absent());
    }

    private Function<Value, Optional<Value>> convert(final Class type) {
        return new Function<Value, Optional<Value>>() {
            @Override
            public Optional<Value> apply(Value input) {
                return configuration.getConverter().convert(input.getValue(), type);
            }
        };
    }

    private Function<InputParameterType, Value> getValue() {
        return new Function<InputParameterType, Value>() {
            @Override
            public Value apply(InputParameterType input) {
                return new Value(configuration.getInputParameterValueResolver().resolve(input));
            }
        };
    }
}
