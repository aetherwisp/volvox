package com.github.aetherwisp.volvox.domain.optional;

import java.util.function.Supplier;

public final class Suppliers {

    //======================================================================
    // Methods
    public static Supplier<IllegalArgumentException> unknownValue(Object _value) {
        return new UnknownValueSupplier(_value);
    }

    //======================================================================
    // Classes
    private static class UnknownValueSupplier implements Supplier<IllegalArgumentException> {
        private static final String FORMAT = "Unknown value. [%s]";

        private final Object value;

        private UnknownValueSupplier(final Object _value) {
            this.value = _value;
        }

        @Override
        public IllegalArgumentException get() {
            return new IllegalArgumentException(String.format(FORMAT, this.value));
        }

    }

}
