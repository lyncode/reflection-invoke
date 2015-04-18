package com.lyncode.reflection;

import com.lyncode.reflection.model.bean.BeanMethod;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExecutableTest {
    private final BeanMethod beanMethod = mock(BeanMethod.class);
    private final Object[] arguments = new Object[0];
    private Executable underTest = new Executable(beanMethod, arguments);

    @Test
    public void get() throws Exception {
        Object value = new Object();
        when(beanMethod.invoke(arguments)).thenReturn(value);

        Object result = underTest.get();

        assertEquals(value, result);
    }
}