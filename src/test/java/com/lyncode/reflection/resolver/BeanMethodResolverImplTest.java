package com.lyncode.reflection.resolver;

import com.google.common.base.Optional;
import com.lyncode.reflection.Executable;
import com.lyncode.reflection.convert.Converter;
import com.lyncode.reflection.input.InputParameterResolver;
import com.lyncode.reflection.input.InputParameterResolverFactory;
import com.lyncode.reflection.input.InputParameterValueResolver;
import com.lyncode.reflection.model.Value;
import com.lyncode.reflection.model.bean.BeanMethod;
import com.lyncode.reflection.model.java.JavaMethod;
import com.lyncode.reflection.model.java.JavaMethodArgument;
import com.lyncode.reflection.resolver.argument.InputArgumentResolver;
import com.lyncode.reflection.resolver.argument.InputArgumentResolverConfiguration;
import com.lyncode.reflection.resolver.argument.InputArgumentResolverFactory;
import com.lyncode.reflection.resolver.argument.ParameterResolver;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class BeanMethodResolverImplTest {
    private final InputArgumentResolverFactory inputArgumentResolverFactory = mock(InputArgumentResolverFactory.class);
    private final ParameterResolver<Integer> parameterResolver = mock(ParameterResolver.class);
    private final BeanMethod beanMethod = mock(BeanMethod.class, RETURNS_DEEP_STUBS);
    private final InputArgumentResolverConfiguration<Integer> configuration = mock(InputArgumentResolverConfiguration.class);
    private final Converter converter = mock(Converter.class);
    private final InputParameterResolverFactory<Integer> inputParameterResolverFactory = mock(InputParameterResolverFactory.class);
    private final InputParameterValueResolver<Integer> inputParameterValueResolver = mock(InputParameterValueResolver.class);
    private final InputParameterResolver inputParameterResolver = mock(InputParameterResolver.class);
    private BeanMethodResolverImpl<Integer> underTest = new BeanMethodResolverImpl<Integer>(converter,
            inputParameterResolverFactory, inputParameterValueResolver,
            inputArgumentResolverFactory, parameterResolver
    );
    private final JavaMethod javaMethod = mock(JavaMethod.class);

    @Test
    public void resolveWhenNoArguments() throws Exception {
        Object value = new Object();
        ArrayList<Integer> inputParameterList = new ArrayList<Integer>();
        when(beanMethod.method().arguments()).thenReturn(new ArrayList<JavaMethodArgument>());
        when(beanMethod.invoke(new Object[0])).thenReturn(value);

        Optional<Executable> result = underTest.resolve(beanMethod, inputParameterList);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get().get(), is(value));
    }

    @Test
    public void resolveWhenOneArgumentNotResolved() throws Exception {
        ArrayList<Integer> inputParameterList = new ArrayList<Integer>();
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);
        InputArgumentResolver inputArgumentResolver = mock(InputArgumentResolver.class);
        when(beanMethod.method().arguments()).thenReturn(asList(javaMethodArgument));
        when(inputArgumentResolverFactory.create(any(Converter.class), any(InputParameterResolver.class), eq(inputParameterValueResolver), anyList())).thenReturn(inputArgumentResolver);
        when(parameterResolver.resolve(inputArgumentResolver, javaMethodArgument)).thenReturn(Optional.<Value>absent());

        Optional<Executable> result = underTest.resolve(beanMethod, inputParameterList);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenAllArgumentsResolved() throws Exception {
        Object value = new Object();
        ArrayList<Integer> inputParameterList = new ArrayList<Integer>();
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);
        InputArgumentResolver<Integer> inputArgumentResolver = mock(InputArgumentResolver.class);
        when(inputArgumentResolverFactory.create(any(Converter.class), any(InputParameterResolver.class), eq(inputParameterValueResolver), anyList())).thenReturn(inputArgumentResolver);
        when(beanMethod.method().arguments()).thenReturn(asList(javaMethodArgument));

        when(parameterResolver.resolve(inputArgumentResolver, javaMethodArgument)).thenReturn(Optional.of(new Value(2)));
        when(beanMethod.invoke(new Object[]{2})).thenReturn(value);

        Optional<Executable> result = underTest.resolve(beanMethod, inputParameterList);

        assertThat(result.isPresent(), is(true));
        assertEquals(value, result.get().get());
    }

    @Test
    public void resolveWhenSomeUnresolvedInputArguments() throws Exception {
        List<Integer> inputParameterList = Arrays.<Integer>asList(2);
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);
        InputArgumentResolver<Integer> inputArgumentResolver = mock(InputArgumentResolver.class);
        when(inputArgumentResolverFactory.create(any(Converter.class), any(InputParameterResolver.class), eq(inputParameterValueResolver), anyList())).thenReturn(inputArgumentResolver);
        when(beanMethod.method().arguments()).thenReturn(asList(javaMethodArgument));
        when(parameterResolver.resolve(inputArgumentResolver, javaMethodArgument)).thenReturn(Optional.of(new Value(1)));

        Optional<Executable> result = underTest.resolve(beanMethod, inputParameterList);

        assertThat(result.isPresent(), is(false));
    }
}