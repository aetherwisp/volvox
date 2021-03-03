package com.github.aetherwisp.volvox.presentation.lib.smartclient;

import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SmartClientSkinFilterTest {

    @Test
    public void testCookieMaxAge() throws Exception {
        Assertions.assertEquals(31556952, Long.valueOf(ChronoUnit.YEARS.getDuration()
            .getSeconds())
            .intValue());

    }
}
