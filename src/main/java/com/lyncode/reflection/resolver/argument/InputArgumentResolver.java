package com.lyncode.reflection.resolver.argument;

import com.google.common.base.Optional;
import com.lyncode.reflection.input.InputParameterResolverContext;
import com.lyncode.reflection.model.Value;
import com.lyncode.reflection.model.java.JavaMethodArgument;

public class InputArgumentResolver<InputParameterType> implements ArgumentResolver {
    private final InputArgumentValueResolver<InputParameterType> resolver;
    private final InputParameterResolverContext<InputParameterType> context;

    public InputArgumentResolver(InputArgumentValueResolver<InputParameterType> resolver,
                                 InputParameterResolverContext<InputParameterType> context) {
        this.resolver = resolver;
        this.context = context;
    }

    @Override
    public Optional<Value> resolve(final JavaMethodArgument methodArgument) {
        InputParameterResolverContext<InputParameterType> clonedContext = context.clone();

        Optional<Value> result = resolver.resolve(methodArgument, clonedContext);

        if (result.isPresent()) {
            context.merge(clonedContext);
        }

        return result;
    }

    public boolean isFullyResolved () {
        return context.fullyUsed();
    }

}
