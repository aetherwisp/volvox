package com.github.aetherwisp.volvox.infrastructure;

import java.util.Objects;
import org.springframework.core.convert.ConversionService;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import com.github.aetherwisp.volvox.domain.Repository.Finder;

public abstract class AbstractJdbcFinder<ENTITY, ID> implements Finder<ENTITY, ID> {

    //======================================================================
    // Fields
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ConversionService conversionService;

    //======================================================================
    // Constructors
    protected AbstractJdbcFinder(final NamedParameterJdbcTemplate _jdbcTemplate, final ConversionService _conversionService) {
        this.jdbcTemplate = Objects.requireNonNull(_jdbcTemplate);
        this.conversionService = Objects.requireNonNull(_conversionService);
    }

    //======================================================================
    // Methods
    protected NamedParameterJdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    protected <T> BeanPropertyRowMapper<T> getRowMapper(Class<T> _mappedClass) {
        final BeanPropertyRowMapper<T> rowMapper = new BeanPropertyRowMapper<>(_mappedClass);
        rowMapper.setConversionService(this.conversionService);
        return rowMapper;
    }

}
