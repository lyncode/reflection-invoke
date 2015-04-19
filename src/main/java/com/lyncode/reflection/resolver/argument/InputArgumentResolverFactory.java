package com.lyncode.reflection.resolver.argument;

import com.lyncode.reflection.convert.Converter;
import com.lyncode.reflection.input.*;
import com.lyncode.reflection.model.bean.BeanMethod;

import java.util.List;

public class InputArgumentResolverFactory<InputParameterType> {
    private final Converter converter;
    private final InputParameterValueResolver<InputParameterType> inputParameterValueResolver;
    private final InputParameterResolverFactory<InputParameterType> inputParameterResolverFactory;
    private final InputParameterResolverContextFactory<InputParameterType> inputParameterResolverContextFactory;

    public InputArgumentResolverFactory(Converter converter, InputParameterValueResolver<InputParameterType> inputParameterValueResolver, InputParameterResolverFactory<InputParameterType> inputParameterResolverFactory, InputParameterResolverContextFactory<InputParameterType> inputParameterResolverContextFactory) {
        this.converter = converter;
        this.inputParameterValueResolver = inputParameterValueResolver;
        this.inputParameterResolverFactory = inputParameterResolverFactory;
        this.inputParameterResolverContextFactory = inputParameterResolverContextFactory;
    }

    public InputArgumentResolver<InputParameterType> create(BeanMethod beanMethod, List<InputParameterType> inputParameterList) {
        InputParameterResolver<InputParameterType> inputParameterResolver = inputParameterResolverFactory.create(beanMethod.method(), inputParameterList);
        InputParameterResolverContext<InputParameterType> context = inputParameterResolverContextFactory.create(inputParameterList);
        InputArgumentResolverConfiguration<InputParameterType> configuration = new InputArgumentResolverConfiguration<InputParameterType>(converter, inputParameterResolver, inputParameterValueResolver        );
        InputArgumentValueResolver<InputParameterType> inputArgumentValueResolver = new InputArgumentValueResolver<InputParameterType>(configuration);
        return new InputArgumentResolver<InputParameterType>(inputArgumentValueResolver, context);
    }
}
