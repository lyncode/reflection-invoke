package com.lyncode.reflection.resolver.argument;

import com.lyncode.reflection.convert.Converter;
import com.lyncode.reflection.input.InputParameterResolver;
import com.lyncode.reflection.input.InputParameterResolverContextFactory;
import com.lyncode.reflection.input.InputParameterResolverFactory;
import com.lyncode.reflection.input.InputParameterValueResolver;
import com.lyncode.reflection.model.bean.BeanMethod;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

public class InputArgumentResolverFactoryTest {
    private final Converter converter = mock(Converter.class);
    private final InputParameterResolver inputParameterResolver = mock(InputParameterResolver.class);
    private final InputParameterValueResolver inputParameterValueResolver = mock(InputParameterValueResolver.class);
    private final InputParameterResolverFactory inputParameterResolverFactory = mock(InputParameterResolverFactory.class);
    private final InputParameterResolverContextFactory parameterResolverContextFactory = mock(InputParameterResolverContextFactory.class);
    private InputArgumentResolverFactory underTest = new InputArgumentResolverFactory(converter, inputParameterValueResolver, inputParameterResolverFactory, parameterResolverContextFactory);

    @Test
    public void create() throws Exception {
        BeanMethod beanMethod = mock(BeanMethod.class);
        ArrayList inputParameterList = new ArrayList();

        InputArgumentResolver<Integer> result = underTest.create(beanMethod, inputParameterList);

        assertNotNull(result);
    }
}