package com.github.aetherwisp.volvox.domain.environment;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class TestEnvironmentService implements EnvironmentService, InitializingBean {
    //======================================================================
    // Initializing
    @Override
    public void afterPropertiesSet() throws Exception {
        Environments.initOnceOnly(this);
    }

    //======================================================================
    // Methods
    @Override
    public Instant currentInstant() {
        return Instant.now();
    }

    @Override
    public LocalDateTime currentLocalDateTime() {
        return LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
    }


}
