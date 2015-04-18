package com.lyncode.reflection.resolver;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.lyncode.reflection.Executable;
import com.lyncode.reflection.convert.Converter;
import com.lyncode.reflection.input.InputParameter;
import com.lyncode.reflection.input.InputParameterResolver;
import com.lyncode.reflection.input.InputParameterResolverFactory;
import com.lyncode.reflection.input.InputParameterValueResolver;
import com.lyncode.reflection.model.Value;
import com.lyncode.reflection.model.bean.BeanMethod;
import com.lyncode.reflection.model.java.JavaMethodArgument;
import com.lyncode.reflection.resolver.argument.InputArgumentResolver;
import com.lyncode.reflection.resolver.argument.InputArgumentResolverConfiguration;
import com.lyncode.reflection.resolver.argument.InputArgumentResolverFactory;
import com.lyncode.reflection.resolver.argument.ParameterResolver;
import com.lyncode.reflection.util.Lists2;

import java.util.ArrayList;
import java.util.List;

public class BeanMethodResolverImpl<InputParameterType> implements BeanMethodResolver<InputParameterType> {
    private final Converter converter;
    private final InputParameterResolverFactory<InputParameterType> inputParameterResolverFactory;
    private final InputParameterValueResolver<InputParameterType> inputParameterValueResolver;
    private final InputArgumentResolverFactory inputArgumentResolverFactory;
    private final ParameterResolver<InputParameterType> parameterResolver;

    public BeanMethodResolverImpl(Converter converter, InputParameterResolverFactory<InputParameterType> inputParameterResolverFactory, InputParameterValueResolver<InputParameterType> inputParameterValueResolver, InputArgumentResolverFactory inputArgumentResolverFactory, ParameterResolver<InputParameterType> parameterResolver) {
        this.converter = converter;
        this.inputParameterResolverFactory = inputParameterResolverFactory;
        this.inputParameterValueResolver = inputParameterValueResolver;
        this.inputArgumentResolverFactory = inputArgumentResolverFactory;
        this.parameterResolver = parameterResolver;
    }

    @Override
    public Optional<Executable> resolve(BeanMethod beanMethod, List<InputParameterType> inputParameterList) {
        List<Object> arguments = new ArrayList<Object>();
        List<InputParameter<InputParameterType>> inputParameters = Lists2.transform(inputParameterList, toInputParameter());
        InputParameterResolver<InputParameterType> inputParameterResolver = inputParameterResolverFactory.create(beanMethod.method(), inputParameterList);
        InputArgumentResolver<InputParameterType> inputArgumentResolver = inputArgumentResolverFactory.create(converter, inputParameterResolver, inputParameterValueResolver, inputParameters);

        for (JavaMethodArgument javaMethodArgument : beanMethod.method().arguments()) {
            Optional<Value> resolve = parameterResolver.resolve(inputArgumentResolver, javaMethodArgument);

            if (!resolve.isPresent()) {
                return Optional.absent();
            }

            arguments.add(resolve.get().getValue());
        }

        return unusedInputParameters(inputParameters) ?
                Optional.<Executable>absent() :
                Optional.of(new Executable(beanMethod, arguments.toArray()));
    }

    private boolean unusedInputParameters(List<InputParameter<InputParameterType>> inputParameters) {
        return !Lists2.filter(inputParameters, unused()).isEmpty();
    }

    private Predicate<InputParameter<InputParameterType>> unused() {
        return new Predicate<InputParameter<InputParameterType>>() {
            @Override
            public boolean apply(InputParameter<InputParameterType> input) {
                return !input.isUsed();
            }
        };
    }

    private Function<InputParameterType, InputParameter<InputParameterType>> toInputParameter() {
        return new Function<InputParameterType, InputParameter<InputParameterType>>() {
            @Override
            public InputParameter<InputParameterType> apply(InputParameterType input) {
                return new InputParameter<InputParameterType>(input, false);
            }
        };
    }
}
