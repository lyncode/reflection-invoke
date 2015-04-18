package com.lyncode.reflection;

import com.lyncode.reflection.convert.CompositeConverter;
import com.lyncode.reflection.convert.Converter;
import com.lyncode.reflection.convert.IdentityConverter;
import com.lyncode.reflection.input.InputParameterResolverFactory;
import com.lyncode.reflection.input.InputParameterValueResolver;
import com.lyncode.reflection.resolver.BeanMethodResolverImpl;
import com.lyncode.reflection.resolver.argument.ArgumentResolver;
import com.lyncode.reflection.resolver.argument.CompositeArgumentResolver;
import com.lyncode.reflection.resolver.argument.InputArgumentResolverFactory;
import com.lyncode.reflection.resolver.argument.ParameterResolver;

import java.util.ArrayList;
import java.util.Collection;

public class MethodInvokerBuilder<T> {
    private final Collection<Converter> converters = new ArrayList<Converter>() {{
        add(new IdentityConverter());
    }};
    private final Collection<ArgumentResolver> argumentResolvers = new ArrayList<ArgumentResolver>();
    private InputParameterValueResolver<T> inputParameterValueResolver;
    private InputParameterResolverFactory<T> inputParameterResolverFactory;

    public MethodInvokerBuilder<T> withInputParameterValueResolver(InputParameterValueResolver<T> inputParameterValueResolver) {
        this.inputParameterValueResolver = inputParameterValueResolver;
        return this;
    }


    public MethodInvokerBuilder<T> withInputParameterResolverFactory(InputParameterResolverFactory<T> inputParameterResolver) {
        this.inputParameterResolverFactory = inputParameterResolver;
        return this;
    }

    public MethodInvokerBuilder<T> withConverter (Converter converter) {
        this.converters.add(converter);
        return this;
    }

    public MethodInvokerBuilder<T> withConverters (Collection<Converter> converter) {
        this.converters.addAll(converter);
        return this;
    }

    public MethodInvokerBuilder<T> withArgumentResolver (ArgumentResolver resolver) {
        this.argumentResolvers.add(resolver);
        return this;
    }

    public MethodInvokerBuilder<T> withArgumentResolvers (Collection<ArgumentResolver> resolvers) {
        this.argumentResolvers.addAll(resolvers);
        return this;
    }

    public MethodInvoker<T> build () {
        return new MethodInvokerImpl<T>(new BeanMethodResolverImpl<T>(
                new CompositeConverter(converters), inputParameterResolverFactory, inputParameterValueResolver,
                new InputArgumentResolverFactory(),
                new ParameterResolver<T>(new CompositeArgumentResolver(argumentResolvers))
        ));
    }
}
