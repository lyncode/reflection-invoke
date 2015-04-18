package com.lyncode.reflection.input;

import com.google.common.base.Optional;
import com.lyncode.reflection.model.java.JavaMethodArgument;

import java.util.List;

public interface InputParameterResolver<InputParameterType> {
    Optional<InputParameterType> resolve (JavaMethodArgument methodArgument, List<InputParameter<InputParameterType>> inputParameters);
}
