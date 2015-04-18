package com.lyncode.reflection.resolver.argument;


import com.google.common.base.Optional;
import com.lyncode.reflection.convert.Converter;
import com.lyncode.reflection.input.InputParameter;
import com.lyncode.reflection.input.InputParameterResolver;
import com.lyncode.reflection.input.InputParameterValueResolver;
import com.lyncode.reflection.model.Value;
import com.lyncode.reflection.model.java.JavaMethodArgument;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InputArgumentResolverTest {
    private final InputParameterResolver<Integer> inputParameterResolver = mock(InputParameterResolver.class);
    private final ArrayList<InputParameter<Integer>> inputParameters = new ArrayList<InputParameter<Integer>>();
    private final InputParameterValueResolver<Integer> inputParameterValueResolver = mock(InputParameterValueResolver.class);
    private final Converter converter = mock(Converter.class);
    private final JavaMethodArgument methodArgument = mock(JavaMethodArgument.class);
    private final InputArgumentResolverConfiguration<Integer> configuration = new InputArgumentResolverConfiguration<Integer>(
            converter, inputParameterResolver, inputParameterValueResolver
    );
    private InputArgumentResolver<Integer> underTest = new InputArgumentResolver<Integer>(
            configuration,
            inputParameters
    );

    @Test
    public void resolveWhenParameterResolverReturnsEmpty() throws Exception {
        when(inputParameterResolver.resolve(methodArgument, inputParameters)).thenReturn(Optional.<Integer>absent());

        Optional<Value> result = underTest.resolve(methodArgument);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenParameterNotConvertible() throws Exception {
        when(inputParameterResolver.resolve(methodArgument, inputParameters)).thenReturn(Optional.of(1));
        when(inputParameterValueResolver.resolve(1)).thenReturn(1);
        when(converter.convert(1, String.class)).thenReturn(Optional.<Value>absent());
        when(methodArgument.type()).thenReturn(String.class);

        Optional<Value> result = underTest.resolve(methodArgument);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenParameterConvertible() throws Exception {
        String value = "wow";
        inputParameters.add(new InputParameter<Integer>(1, false));
        when(inputParameterResolver.resolve(methodArgument, inputParameters)).thenReturn(Optional.of(1));
        when(inputParameterValueResolver.resolve(1)).thenReturn(1);
        when(converter.convert(1, String.class)).thenReturn(Optional.of(new Value(value)));
        when(methodArgument.type()).thenReturn(String.class);

        Optional<Value> result = underTest.resolve(methodArgument);

        assertThat(result.isPresent(), is(true));
        assertEquals(value, result.get().getValue());
        assertThat(inputParameters.get(0).isUsed(), is(true));
    }
}