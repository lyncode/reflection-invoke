package com.lyncode.reflection.convert;

import com.google.common.base.Optional;
import com.lyncode.reflection.model.Value;

public interface Converter {
    Optional<Value> convert (Object input, Class to);
}
