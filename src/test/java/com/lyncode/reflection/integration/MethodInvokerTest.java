package com.lyncode.reflection.integration;

import com.lyncode.reflection.MethodInvoker;
import com.lyncode.reflection.MethodInvokerBuilder;
import com.lyncode.reflection.extractor.BeanMethodExtractor;
import com.lyncode.reflection.input.InputParameterResolver;
import com.lyncode.reflection.input.InputParameterResolverFactory;
import com.lyncode.reflection.integration.example.ObjectToIntegerConverter;
import com.lyncode.reflection.integration.example.TestArgument;
import com.lyncode.reflection.integration.example.TestArgumentResolver;
import com.lyncode.reflection.integration.example.TestArgumentValueResolver;
import com.lyncode.reflection.model.bean.BeanMethod;
import com.lyncode.reflection.model.java.JavaMethod;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;
import static junit.framework.TestCase.assertEquals;

public class MethodInvokerTest {
    private final BeanMethodExtractor extractor = new BeanMethodExtractor();
    private final MethodInvoker<TestArgument> invoker = new MethodInvokerBuilder<TestArgument>()
            .withInputParameterResolverFactory(new InputParameterResolverFactory<TestArgument>() {
                @Override
                public InputParameterResolver<TestArgument> create(JavaMethod method, List<TestArgument> testArguments) {
                    return new TestArgumentResolver();
                }
            })
            .withInputParameterValueResolver(new TestArgumentValueResolver())
            .withConverter(new ObjectToIntegerConverter())
            .build();

    @Test
    public void oneArgument() throws Exception {
        Collection<BeanMethod> beanMethods = extractor.extract(new MyTest());
        Object result = invoker.invoke(new MethodInvoker.Request<TestArgument>(beanMethods,
                asList(new TestArgument("one")))).get().get();

        assertEquals("one3", result);
    }

    @Test
    public void twoArguments() throws Exception {
        Collection<BeanMethod> beanMethods = extractor.extract(new MyTest());
        Object result = invoker.invoke(new MethodInvoker.Request<TestArgument>(beanMethods,
                asList(new TestArgument("one"), new TestArgument(" hehe")))).get().get();

        assertEquals("one hehe", result);
    }

    @Test
    public void integerConvertedArgument() throws Exception {
        Collection<BeanMethod> beanMethods = extractor.extract(new MyTest());
        Object result = invoker.invoke(new MethodInvoker.Request<TestArgument>(beanMethods,
                asList(new TestArgument(1.1D)))).get().get();

        assertEquals("13", result);
    }

    public static class MyTest {
        public String one (Integer value) {
            return value + "3";
        }

        public String one (String value) {
            return value + "3";
        }

        public String one (String value, String va) {
            return value + va;
        }
    }
}
