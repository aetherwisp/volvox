package com.github.aetherwisp.volvox.domain.environment;

import java.time.Instant;
import java.time.LocalDateTime;

public interface EnvironmentService {

    Instant currentInstant();

    LocalDateTime currentLocalDateTime();
}
