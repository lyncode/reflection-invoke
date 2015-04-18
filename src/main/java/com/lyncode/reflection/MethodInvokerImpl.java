package com.lyncode.reflection;

import com.google.common.base.Optional;
import com.lyncode.reflection.model.bean.BeanMethod;
import com.lyncode.reflection.resolver.BeanMethodResolver;

class MethodInvokerImpl<InputParameterType> implements MethodInvoker<InputParameterType> {
    private final BeanMethodResolver<InputParameterType> beanMethodResolver;

    public MethodInvokerImpl(BeanMethodResolver<InputParameterType> beanMethodResolver) {
        this.beanMethodResolver = beanMethodResolver;
    }

    @Override
    public Optional<Executable> invoke(Request<InputParameterType> request) {
        for (BeanMethod beanMethod : request.getBeanMethods()) {
            Optional<Executable> result = beanMethodResolver.resolve(beanMethod, request.getInputParameters());
            if (result.isPresent()) {
                return result;
            }
        }
        return Optional.absent();
    }
}
