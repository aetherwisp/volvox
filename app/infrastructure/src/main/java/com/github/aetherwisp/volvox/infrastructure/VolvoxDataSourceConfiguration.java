package com.github.aetherwisp.volvox.infrastructure;

import static com.github.aetherwisp.volvox.Volvox.Profiles.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({DEV, STG, PRD})
public class VolvoxDataSourceConfiguration {
}
