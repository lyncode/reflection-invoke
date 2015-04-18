package com.lyncode.reflection.resolver.argument;

import com.lyncode.reflection.convert.Converter;
import com.lyncode.reflection.input.InputParameter;
import com.lyncode.reflection.input.InputParameterResolver;
import com.lyncode.reflection.input.InputParameterValueResolver;

import java.util.List;

public class InputArgumentResolverFactory {
    public <InputParameterType> InputArgumentResolver<InputParameterType> create(Converter converter, InputParameterResolver<InputParameterType> inputParameterResolver, InputParameterValueResolver<InputParameterType> inputParameterValueResolver, List<InputParameter<InputParameterType>> inputParameters) {
        return new InputArgumentResolver<InputParameterType>(new InputArgumentResolverConfiguration<InputParameterType>(
                converter, inputParameterResolver, inputParameterValueResolver
        ), inputParameters);
    }
}
