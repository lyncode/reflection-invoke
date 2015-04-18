package com.lyncode.reflection.resolver.argument;

import com.google.common.base.Optional;
import com.lyncode.reflection.model.Value;
import com.lyncode.reflection.model.java.JavaMethodArgument;

public interface ArgumentResolver {
    Optional<Value> resolve (JavaMethodArgument methodArgument);
}
