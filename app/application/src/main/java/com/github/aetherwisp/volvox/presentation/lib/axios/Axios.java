package com.github.aetherwisp.volvox.presentation.lib.axios;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.github.aetherwisp.volvox.presentation.PresentationConfiguration;

@Component("axios")
public class Axios {
    //======================================================================
    // Constants
    public static final String PREFIX = PresentationConfiguration.PREFIX + ".lib.axios";

    //======================================================================
    // Fields
    private final String version;

    //======================================================================
    // Constructors
    @Autowired
    public Axios(@Value("${" + Axios.PREFIX + ".version}") final String _version) {
        this.version = Objects.requireNonNull(_version);
    }

    //======================================================================
    // Getters
    public String getVersion() {
        return this.version;
    }
}
