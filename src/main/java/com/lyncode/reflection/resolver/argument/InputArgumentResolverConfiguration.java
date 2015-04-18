package com.lyncode.reflection.resolver.argument;

import com.lyncode.reflection.convert.Converter;
import com.lyncode.reflection.input.InputParameterResolver;
import com.lyncode.reflection.input.InputParameterResolverFactory;
import com.lyncode.reflection.input.InputParameterValueResolver;

public class InputArgumentResolverConfiguration<InputParameterType> {
    private final Converter converter;
    private final InputParameterResolver<InputParameterType> inputParameterResolver;
    private final InputParameterValueResolver<InputParameterType> inputParameterValueResolver;


    public InputArgumentResolverConfiguration(Converter converter, InputParameterResolver<InputParameterType> inputParameterResolver, InputParameterValueResolver<InputParameterType> inputParameterValueResolver) {
        this.converter = converter;
        this.inputParameterResolver = inputParameterResolver;
        this.inputParameterValueResolver = inputParameterValueResolver;
    }

    public Converter getConverter() {
        return converter;
    }

    public InputParameterValueResolver<InputParameterType> getInputParameterValueResolver() {
        return inputParameterValueResolver;
    }

    public InputParameterResolver<InputParameterType> getInputParameterResolver() {
        return inputParameterResolver;
    }
}
