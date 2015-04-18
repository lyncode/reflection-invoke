package com.lyncode.reflection.resolver.argument;

import com.google.common.base.Optional;
import com.lyncode.reflection.model.Value;
import com.lyncode.reflection.model.java.JavaMethodArgument;

import java.util.Collection;

public class CompositeArgumentResolver implements ArgumentResolver {
    private final Collection<ArgumentResolver> argumentResolvers;

    public CompositeArgumentResolver(Collection<ArgumentResolver> argumentResolvers) {
        this.argumentResolvers = argumentResolvers;
    }

    @Override
    public Optional<Value> resolve(JavaMethodArgument methodArgument) {
        for (ArgumentResolver argumentResolver : argumentResolvers) {
            Optional<Value> resolve = argumentResolver.resolve(methodArgument);
            if (resolve.isPresent()) {
                return resolve;
            }
        }
        return Optional.absent();
    }
}
