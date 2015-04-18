package com.lyncode.reflection.resolver.argument;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.lyncode.reflection.input.InputParameter;
import com.lyncode.reflection.model.Value;
import com.lyncode.reflection.model.java.JavaMethodArgument;
import com.lyncode.reflection.util.Lists2;

import java.util.List;

public class InputArgumentResolver<InputParameterType> implements ArgumentResolver {
    private final InputArgumentResolverConfiguration<InputParameterType> configuration;
    private final List<InputParameter<InputParameterType>> inputParameters;

    public InputArgumentResolver(InputArgumentResolverConfiguration<InputParameterType> configuration, List<InputParameter<InputParameterType>> inputParameters) {
        this.configuration = configuration;
        this.inputParameters = inputParameters;
    }

    @Override
    public Optional<Value> resolve(final JavaMethodArgument methodArgument) {
        return configuration.getInputParameterResolver().resolve(methodArgument, inputParameters)
                .transform(getValue())
                .transform(convertToExpected(methodArgument))
                .transform(markAsUsedAndExtract())
                .or(Optional.<Optional<Value>>absent())
                .or(Optional.<Value>absent());
    }

    private Function<InputValue<InputParameterType, Optional<Value>>, Optional<Value>> markAsUsedAndExtract() {
        return new Function<InputValue<InputParameterType, Optional<Value>>, Optional<Value>>() {
            @Override
            public Optional<Value> apply(final InputValue<InputParameterType, Optional<Value>> input) {
                if (input.value.isPresent()) {
                    int position = Lists2.positionOf(inputParameters, new Predicate<InputParameter<InputParameterType>>() {
                        @Override
                        public boolean apply(InputParameter<InputParameterType> parameter) {
                            return parameter.getParameter() == input.parameter;
                        }
                    });
                    if (position >= 0) {
                        inputParameters.set(position,
                                new InputParameter<InputParameterType>(inputParameters.get(position).getParameter(), true));
                    }
                }
                return input.value;
            }
        };
    }

    private Function<InputParameterType, InputValue<InputParameterType, Value>> getValue() {
        return new Function<InputParameterType, InputValue<InputParameterType, Value>>() {
            @Override
            public InputValue<InputParameterType, Value> apply(InputParameterType input) {
                return new InputValue<InputParameterType, Value>(input, new Value(configuration.getInputParameterValueResolver().resolve(input)));
            }
        };
    }

    private Function<InputValue<InputParameterType, Value>, InputValue<InputParameterType, Optional<Value>>> convertToExpected(final JavaMethodArgument methodArgument) {
        return new Function<InputValue<InputParameterType, Value>, InputValue<InputParameterType, Optional<Value>>>() {
            @Override
            public InputValue<InputParameterType, Optional<Value>> apply(InputValue<InputParameterType, Value> input) {
                return input.mapValue(convert(methodArgument));
            }
        };
    }

    private Function<Value, Optional<Value>> convert(final JavaMethodArgument methodArgument) {
        return new Function<Value, Optional<Value>>() {
            @Override
            public Optional<Value> apply(Value input) {
                return configuration.getConverter().convert(input.getValue(), methodArgument.type());
            }
        };
    }

    private static class InputValue<T, V> {
        private final T parameter;
        private final V value;

        public InputValue(T parameter, V value) {
            this.parameter = parameter;
            this.value = value;
        }

        public <W> InputValue<T, W> mapValue (Function<V, W> transform) {
            return new InputValue<T, W>(parameter, transform.apply(value));
        }
    }
}
