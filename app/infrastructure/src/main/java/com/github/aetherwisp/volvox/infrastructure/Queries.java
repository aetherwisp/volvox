package com.github.aetherwisp.volvox.infrastructure;

import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.lang.Nullable;

public final class Queries {
    private Queries() {
        throw new UnsupportedOperationException();
    }

    //======================================================================
    // Methods
    public static QueryBuilder query() {
        return new QueryBuilder();
    }

    public static QueryParameters parameters() {
        return new QueryParameters();
    }

    //======================================================================
    // Classes
    public static final class QueryBuilder {
        //======================================================================
        // Fields
        private final StringBuilder builder;

        //======================================================================
        // Constructors
        private QueryBuilder() {
            this.builder = new StringBuilder();
        }

        //======================================================================
        // Methods
        public QueryBuilder append(String _str) {
            this.builder.append(_str);
            return this;
        }

        public QueryBuilder append(String _str, boolean _append) {
            if (_append) {
                this.builder.append(_str);
            }
            return this;
        }

        @Override
        public String toString() {
            return Optional.ofNullable(this.builder.toString())
                    .orElse("")
                    .replaceAll(" +", " ");
        }
    }

    public static final class QueryParameters implements SqlParameterSource {
        //======================================================================
        // Fields
        private final MapSqlParameterSource source = new MapSqlParameterSource();

        //======================================================================
        // Constructors
        private QueryParameters() {}

        //======================================================================
        // Methods
        public QueryParameters addValue(String _paramName, @Nullable Object _value, boolean _use) {
            if (_use) {
                this.source.addValue(_paramName, _value);
            }
            return this;
        }

        public QueryParameters addValue(String _paramName, @Nullable Object _value, int _sqlType) {
            return this.addValue(_paramName, _value, _sqlType, true);
        }

        public QueryParameters addValue(String _paramName, @Nullable Object _value, int _sqlType, boolean _use) {
            if (_use) {
                this.source.addValue(_paramName, _value, _sqlType);
            }
            return this;
        }

        public QueryParameters addValue(String _paramName, @Nullable Object _value, int _sqlType, String _typeName, boolean _use) {
            if (_use) {
                this.source.addValue(_paramName, _value, _sqlType, _typeName);
            }
            return this;
        }

        public QueryParameters addValues(@Nullable Map<String, ?> _values) {
            this.source.addValues(_values);
            return this;
        }

        @Override
        public boolean hasValue(String _paramName) {
            return this.source.hasValue(_paramName);
        }

        @Override
        public Object getValue(String _paramName) throws IllegalArgumentException {
            return this.source.getValue(_paramName);
        }
    }
}
