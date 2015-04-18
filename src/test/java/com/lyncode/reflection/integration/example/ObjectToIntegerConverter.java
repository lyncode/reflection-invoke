package com.lyncode.reflection.integration.example;

import com.google.common.base.Optional;
import com.lyncode.reflection.convert.Converter;
import com.lyncode.reflection.model.Value;

import java.math.BigDecimal;

public class ObjectToIntegerConverter implements Converter {
    @Override
    public Optional<Value> convert(Object input, Class to) {
        if (to.equals(Integer.class) || to.equals(Integer.TYPE)) {
            try {
                return Optional.of(new Value(new BigDecimal(input.toString()).intValue()));
            } catch (NumberFormatException ex) {
                return Optional.absent();
            }
        } else {
            return Optional.absent();
        }
    }
}
