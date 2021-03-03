package com.github.aetherwisp.volvox.presentation;

import java.util.Objects;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import com.github.aetherwisp.volvox.Volvox;

@Component
public class PresentationConfiguration {
    //======================================================================
    // Constants
    public static final String PREFIX = Volvox.CONFIG_ROOT + ".presentation";

    //======================================================================
    // Fields
    private final ResourceBundleMessageSource messageSource;

    //======================================================================
    // Constructors
    @Autowired
    public PresentationConfiguration(final ResourceBundleMessageSource _messageSource) {
        this.messageSource = Objects.requireNonNull(_messageSource);
    }

    //======================================================================
    // Methods
    /**
     * Add volvox-presentation specific message source.
     */
    @PostConstruct
    public void initialize() {
        this.messageSource.addBasenames("i18n/volvox-presentation-messages");
    }

}
