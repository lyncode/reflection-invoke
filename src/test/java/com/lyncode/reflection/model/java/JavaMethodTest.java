package com.lyncode.reflection.model.java;

import com.google.common.base.Optional;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class JavaMethodTest {
    private JavaMethod underTest;

    @Test
    public void arguments() throws Exception {
        underTest = new JavaMethod(TestClass.class.getDeclaredMethod("test"));

        assertThat(underTest.arguments().size(), is(0));
    }

    @Test
    public void invoke() throws Exception {
        underTest = new JavaMethod(TestClass.class.getDeclaredMethod("test", String.class));

        assertEquals("blah", underTest.invoke(new TestClass(), new Object[]{"blah"}));
    }

    @Test
    public void type() throws Exception {
        underTest = new JavaMethod(TestClass.class.getDeclaredMethod("test", String.class));

        assertEquals(String.class, underTest.type(0));
    }

    @Test
    public void numberOfArguments() throws Exception {
        underTest = new JavaMethod(TestClass.class.getDeclaredMethod("a", String[].class));

        assertThat(underTest.numberOfArguments(), is(1));
    }

    @Test
    public void isVarArgs() throws Exception {
        underTest = new JavaMethod(TestClass.class.getDeclaredMethod("a", String[].class));

        assertThat(underTest.isVarArgs(), is(true));
    }

    @Test
    public void annotation() throws Exception {
        underTest = new JavaMethod(TestClass.class.getDeclaredMethod("test"));

        Optional<Test> result = underTest.annotation(Test.class);

        assertThat(result.isPresent(), is(true));
    }

    public static class TestClass {
        @Test
        public String test () { return "one"; }
        public String test (String arg) { return arg; }
        public String a (String... arg) { return "a"; }
    }
}