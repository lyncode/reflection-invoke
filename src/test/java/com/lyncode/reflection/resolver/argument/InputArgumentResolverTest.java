package com.lyncode.reflection.resolver.argument;


import com.google.common.base.Optional;
import com.lyncode.reflection.input.InputParameterResolverContext;
import com.lyncode.reflection.model.Value;
import com.lyncode.reflection.model.java.JavaMethodArgument;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class InputArgumentResolverTest {
    private final JavaMethodArgument methodArgument = mock(JavaMethodArgument.class);
    private final InputParameterResolverContext<Integer> context = mock(InputParameterResolverContext.class);
    private InputArgumentValueResolver<Integer> resolver = mock(InputArgumentValueResolver.class);
    private InputArgumentResolver<Integer> underTest = new InputArgumentResolver<Integer>(resolver, context);

    @Before
    public void setUp() throws Exception {
        when(context.clone()).thenReturn(context);
    }

    @Test
    public void resolveWhenParameterResolverReturnsEmpty() throws Exception {
        when(resolver.resolve(methodArgument, context)).thenReturn(Optional.<Value>absent());

        Optional<Value> result = underTest.resolve(methodArgument);

        assertThat(result.isPresent(), is(false));
        verify(context, never()).merge(any(InputParameterResolverContext.class));
    }

    @Test
    public void resolveWhenParameterResolverReturnsSome() throws Exception {
        when(resolver.resolve(methodArgument, context)).thenReturn(Optional.of(new Value("one")));

        Optional<Value> result = underTest.resolve(methodArgument);

        assertThat(result.isPresent(), is(true));
        verify(context).merge(any(InputParameterResolverContext.class));
    }
}