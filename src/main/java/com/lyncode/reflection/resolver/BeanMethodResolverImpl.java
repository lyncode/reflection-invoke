package com.lyncode.reflection.resolver;

import com.google.common.base.Optional;
import com.lyncode.reflection.Executable;
import com.lyncode.reflection.model.Value;
import com.lyncode.reflection.model.bean.BeanMethod;
import com.lyncode.reflection.model.java.JavaMethodArgument;
import com.lyncode.reflection.resolver.argument.InputArgumentResolver;
import com.lyncode.reflection.resolver.argument.InputArgumentResolverFactory;
import com.lyncode.reflection.resolver.argument.ParameterResolver;

import java.util.ArrayList;
import java.util.List;

public class BeanMethodResolverImpl<InputParameterType> implements BeanMethodResolver<InputParameterType> {
    private final InputArgumentResolverFactory<InputParameterType> factory;
    private final ParameterResolver<InputParameterType> parameterResolver;

    public BeanMethodResolverImpl(InputArgumentResolverFactory<InputParameterType> factory,
                                  ParameterResolver<InputParameterType> parameterResolver) {
        this.factory = factory;
        this.parameterResolver = parameterResolver;
    }

    @Override
    public Optional<Executable> resolve(BeanMethod beanMethod, List<InputParameterType> inputParameterList) {
        List<Object> arguments = new ArrayList<Object>();
        InputArgumentResolver<InputParameterType> inputArgumentResolver = factory.create(beanMethod, inputParameterList);

        for (JavaMethodArgument javaMethodArgument : beanMethod.method().arguments()) {
            Optional<Value> resolve = parameterResolver.resolve(inputArgumentResolver, javaMethodArgument);

            if (!resolve.isPresent()) {
                return Optional.absent();
            }

            arguments.add(resolve.get().getValue());
        }

        return inputArgumentResolver.isFullyResolved() ?
                Optional.of(new Executable(beanMethod, arguments.toArray())) :
                Optional.<Executable>absent();
    }
}
