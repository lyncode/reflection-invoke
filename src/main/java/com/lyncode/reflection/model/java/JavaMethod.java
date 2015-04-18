package com.lyncode.reflection.model.java;

import com.google.common.base.Optional;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class JavaMethod {
    private final Method method;

    public JavaMethod(Method method) {
        this.method = method;
    }

    public List<JavaMethodArgument> arguments() {
        List<JavaMethodArgument> result = new ArrayList<JavaMethodArgument>();
        Class<?>[] parameterTypes = method.getParameterTypes();
        for (int i = 0; i < parameterTypes.length; i++) {
            result.add(new JavaMethodArgument(this, i));
        }
        return result;
    }

    public Object invoke(Object bean, Object[] arguments) throws InvocationTargetException, IllegalAccessException {
        return method.invoke(bean, arguments);
    }

    public Class type(int position) {
        return method.getParameterTypes()[position];
    }

    public boolean isVarArgs() {
        return method.isVarArgs();
    }

    public int numberOfArguments() {
        return method.getParameterTypes().length;
    }

    public <T extends Annotation> Optional<T> argumentAnnotation(int position, Class<T> type) {
        Annotation[] annotations = method.getParameterAnnotations()[position];
        for (Annotation annotation : annotations) {
            if (annotation.getClass().equals(type)) {
                return Optional.of(type.cast(annotation));
            }
        }
        return Optional.absent();
    }

    public <T extends Annotation> Optional<T> annotation(Class<T> type) {
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation.getClass().equals(type)) {
                return Optional.of(type.cast(annotation));
            }
        }
        return Optional.absent();
    }

    public String name () {
        return method.getName();
    }

}
