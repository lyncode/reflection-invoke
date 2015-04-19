package com.lyncode.reflection.resolver;

import com.google.common.base.Optional;
import com.lyncode.reflection.Executable;
import com.lyncode.reflection.model.Value;
import com.lyncode.reflection.model.bean.BeanMethod;
import com.lyncode.reflection.model.java.JavaMethodArgument;
import com.lyncode.reflection.resolver.argument.InputArgumentResolver;
import com.lyncode.reflection.resolver.argument.InputArgumentResolverFactory;
import com.lyncode.reflection.resolver.argument.ParameterResolver;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class BeanMethodResolverImplTest {
    private final InputArgumentResolverFactory<Integer> inputArgumentResolverFactory = mock(InputArgumentResolverFactory.class);
    private final ParameterResolver<Integer> parameterResolver = mock(ParameterResolver.class);
    private final InputArgumentResolver<Integer> inputArgumentResolver = mock(InputArgumentResolver.class);
    private final List<Integer> argumentList = new ArrayList<Integer>();
    private final BeanMethod beanMethod = mock(BeanMethod.class, RETURNS_DEEP_STUBS);

    private BeanMethodResolverImpl<Integer> underTest = new BeanMethodResolverImpl<Integer>(inputArgumentResolverFactory, parameterResolver);

    @Before
    public void setUp() throws Exception {
        argumentList.clear();
        when(inputArgumentResolverFactory.create(beanMethod, argumentList)).thenReturn(inputArgumentResolver);
    }

    @Test
    public void resolveWhenNoArgumentsAndFullyResolved() throws Exception {
        when(inputArgumentResolver.isFullyResolved()).thenReturn(true);

        Optional<Executable> result = underTest.resolve(beanMethod, argumentList);

        assertThat(result.isPresent(), is(true));
    }

    @Test
    public void resolveWhenNoArgumentsButNotFullyResolved() throws Exception {
        when(inputArgumentResolver.isFullyResolved()).thenReturn(false);

        Optional<Executable> result = underTest.resolve(beanMethod, argumentList);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenOneArgumentNotResolved() throws Exception {
        argumentList.add(1);
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);
        when(beanMethod.method().arguments()).thenReturn(asList(javaMethodArgument));
        when(parameterResolver.resolve(inputArgumentResolver, javaMethodArgument)).thenReturn(Optional.<Value>absent());

        Optional<Executable> result = underTest.resolve(beanMethod, argumentList);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenAllArgumentsResolved() throws Exception {
        argumentList.add(1);
        String value = "asdsad";
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);
        when(beanMethod.method().arguments()).thenReturn(asList(javaMethodArgument));
        when(parameterResolver.resolve(inputArgumentResolver, javaMethodArgument)).thenReturn(Optional.of(new Value(value)));
        when(inputArgumentResolver.isFullyResolved()).thenReturn(true);

        Optional<Executable> result = underTest.resolve(beanMethod, argumentList);

        assertThat(result.isPresent(), is(true));
    }
}