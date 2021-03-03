package com.github.aetherwisp.volvox.infrastructure;

import static com.github.aetherwisp.volvox.Volvox.Profiles.DEV;
import static com.github.aetherwisp.volvox.Volvox.Profiles.PRD;
import static com.github.aetherwisp.volvox.Volvox.Profiles.STG;
import java.util.Objects;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import com.github.aetherwisp.volvox.domain.context.YamlPropertySourceFactory;

@Configuration
public class VolvoxInfrastructureConfiguration {
    //======================================================================
    // PropertySource Configurations
    @Configuration
    @PropertySource(factory = YamlPropertySourceFactory.class,
            value = {"classpath:config/volvox-infrastructure.yml",
                    "classpath:config/volvox-infrastructure-" + DEV + ".yml"},
            ignoreResourceNotFound = true)
    @Profile(DEV)
    static class DevelopConfiguration {
        // do nothing.
    }

    @Configuration
    @PropertySource(factory = YamlPropertySourceFactory.class,
            value = {"classpath:config/volvox-infrastructure.yml",
                    "classpath:config/volvox-infrastructure-" + STG + ".yml"},
            ignoreResourceNotFound = true)
    @Profile(STG)
    static class StagingConfiguration {
        // do nothing.
    }

    @Configuration
    @PropertySource(factory = YamlPropertySourceFactory.class,
            value = {"classpath:config/volvox-infrastructure.yml",
                    "classpath:config/volvox-infrastructure-" + PRD + ".yml"},
            ignoreResourceNotFound = true)
    @Profile(PRD)
    static class ProductConfiguration {
        // do nothing.
    }

    //======================================================================
    // Fields
    private final ResourceBundleMessageSource messageSource;

    //======================================================================
    // Constructors
    @Autowired
    public VolvoxInfrastructureConfiguration(final ResourceBundleMessageSource _messageSource) {
        this.messageSource = Objects.requireNonNull(_messageSource);
    }

    //======================================================================
    // Methods
    /**
     * Add volvox-infrastructure specific message source.
     */
    @PostConstruct
    public void initialize() {
        this.messageSource.addBasenames("i18n/volvox-infrastructure-messages");
    }

}
