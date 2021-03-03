package com.github.aetherwisp.volvox.application;

import java.util.Objects;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import com.github.aetherwisp.volvox.Volvox;

@Component("volvox")
public class ApplicationConfiguration {
    //======================================================================
    // Constants
    public static final String PREFIX = Volvox.CONFIG_ROOT + ".application";

    //======================================================================
    // Fields
    private final String version;
    private final ResourceBundleMessageSource messageSource;

    //======================================================================
    // Constructors
    @Autowired
    public ApplicationConfiguration(@Value("${" + ApplicationConfiguration.PREFIX + ".version}") final String _version,
            final ResourceBundleMessageSource _messageSource) {
        this.version = Objects.requireNonNull(_version);
        this.messageSource = Objects.requireNonNull(_messageSource);
    }

    //======================================================================
    // Methods
    /**
     * Add volvox-infrastructure specific message source.
     */
    @PostConstruct
    public void initialize() {
        this.messageSource.addBasenames("i18n/volvox-application-messages");
    }

    //======================================================================
    // Getters
    public String getVersion() {
        return this.version;
    }
}
