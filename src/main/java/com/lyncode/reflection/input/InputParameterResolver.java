package com.lyncode.reflection.input;

import com.google.common.base.Optional;
import com.lyncode.reflection.model.java.JavaMethodArgument;

public interface InputParameterResolver<InputParameterType> {
    Optional<InputParameterType> resolve (JavaMethodArgument argument, InputParameterResolverContext<InputParameterType> context);
}
