package com.lyncode.reflection.resolver;

import com.google.common.base.Optional;
import com.lyncode.reflection.Executable;
import com.lyncode.reflection.model.bean.BeanMethod;

import java.util.List;

public interface BeanMethodResolver<InputParameterType> {
    Optional<Executable> resolve (BeanMethod beanMethod, List<InputParameterType> inputParameterList);
}
