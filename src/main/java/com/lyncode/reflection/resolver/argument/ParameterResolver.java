package com.lyncode.reflection.resolver.argument;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import com.lyncode.reflection.model.Value;
import com.lyncode.reflection.model.java.JavaMethodArgument;
import com.lyncode.reflection.util.Optionals;

public class ParameterResolver<InputParameter> {
    private final ArgumentResolver argumentResolver;

    public ParameterResolver(ArgumentResolver argumentResolver) {
        this.argumentResolver = argumentResolver;
    }

    public Optional<Value> resolve (InputArgumentResolver<InputParameter> inputArgumentResolver, JavaMethodArgument methodArgument) {
        return Optionals
                .orOptionalSupplier(
                        inputArgumentResolver.resolve(methodArgument),
                        resolveStaticArgument(methodArgument));
    }

    private Supplier<Optional<Value>> resolveStaticArgument(final JavaMethodArgument javaMethodArgument) {
        return new Supplier<Optional<Value>>() {
            @Override
            public Optional<Value> get() {
                return argumentResolver.resolve(javaMethodArgument);
            }
        };
    }
}
