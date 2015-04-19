package com.lyncode.reflection.resolver.argument;

import com.google.common.base.Optional;
import com.lyncode.reflection.input.InputParameterResolverContext;
import com.lyncode.reflection.model.Value;
import com.lyncode.reflection.model.java.JavaMethodArgument;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class InputArgumentValueResolverTest {
    private final InputArgumentResolverConfiguration configuration = mock(InputArgumentResolverConfiguration.class, RETURNS_DEEP_STUBS);
    private final JavaMethodArgument methodArgument = mock(JavaMethodArgument.class);
    private final InputParameterResolverContext context = mock(InputParameterResolverContext.class);
    private InputArgumentValueResolver underTest = new InputArgumentValueResolver(configuration);

    @Test
    public void resolveWhenInputParameterAbsent() throws Exception {
        when(configuration.getInputParameterResolver().resolve(methodArgument, context)).thenReturn(Optional.absent());

        Optional<Value> result = underTest.resolve(methodArgument, context);

        assertThat(result.isPresent(), is(false));
    }


    @Test
    public void resolveWhenConvertFails() throws Exception {
        when(methodArgument.type()).thenReturn(String.class);
        when(configuration.getInputParameterResolver().resolve(methodArgument, context)).thenReturn(Optional.of("one"));
        when(configuration.getConverter().convert("one", String.class)).thenReturn(Optional.<Value>absent());

        Optional<Value> result = underTest.resolve(methodArgument, context);

        assertThat(result.isPresent(), is(false));
    }


    @Test
    public void resolveWhenConvertWorks() throws Exception {
        when(methodArgument.type()).thenReturn(String.class);
        when(configuration.getInputParameterResolver().resolve(methodArgument, context)).thenReturn(Optional.of("hi"));
        when(configuration.getInputParameterValueResolver().resolve("hi")).thenReturn("one");
        when(configuration.getConverter().convert("one", String.class)).thenReturn(Optional.of(new Value("test")));

        Optional<Value> result = underTest.resolve(methodArgument, context);

        assertThat(result.isPresent(), is(true));
        assertEquals(result.get().getValue(), "test");
    }
}