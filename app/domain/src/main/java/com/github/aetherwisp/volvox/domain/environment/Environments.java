package com.github.aetherwisp.volvox.domain.environment;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public final class Environments {
    private Environments() {
        throw new UnsupportedOperationException();
    }

    private static AtomicReference<EnvironmentService> ref = new AtomicReference<>();

    public static void initOnceOnly(EnvironmentService _environmentService) {
        ref.compareAndSet(null, Objects.requireNonNull(_environmentService));
    }

    public static Instant utcInstant() {
        return ref.get()
                .currentInstant();
    }

    public static LocalDateTime utcDateTime() {
        return ref.get()
                .currentLocalDateTime();
    }
}
