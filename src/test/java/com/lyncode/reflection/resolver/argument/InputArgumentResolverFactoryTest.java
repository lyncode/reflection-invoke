package com.lyncode.reflection.resolver.argument;

import com.lyncode.reflection.convert.Converter;
import com.lyncode.reflection.input.InputParameter;
import com.lyncode.reflection.input.InputParameterResolver;
import com.lyncode.reflection.input.InputParameterValueResolver;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class InputArgumentResolverFactoryTest {
    private final InputArgumentResolverConfiguration<Integer> configuration = mock(InputArgumentResolverConfiguration.class);
    private final Converter converter = mock(Converter.class);
    private final InputParameterResolver inputParameterResolver = mock(InputParameterResolver.class);
    private final InputParameterValueResolver inputParameterValueResolver = mock(InputParameterValueResolver.class);
    private InputArgumentResolverFactory underTest = new InputArgumentResolverFactory();

    @Test
    public void create() throws Exception {
        ArrayList<InputParameter<Integer>> list = new ArrayList<InputParameter<Integer>>();

        InputArgumentResolver<Integer> result = underTest.create(converter, inputParameterResolver, inputParameterValueResolver, list);

        assertNotNull(result);
    }
}