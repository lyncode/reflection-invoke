package com.lyncode.reflection.input;

import com.lyncode.reflection.model.java.JavaMethod;

import java.util.List;

public interface InputParameterResolverFactory<InputParameter> {
    InputParameterResolver<InputParameter> create (JavaMethod method, List<InputParameter> inputParameters);
}
