package com.lyncode.reflection.convert;

import com.google.common.base.Optional;
import com.lyncode.reflection.model.Value;

public class IdentityConverter implements Converter {
    @Override
    public Optional<Value> convert(Object input, Class to) {
        if (input == null || to.isAssignableFrom(input.getClass())) {
            return Optional.of(new Value(input));
        } else {
            return Optional.absent();
        }
    }
}
