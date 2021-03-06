package com.github.aetherwisp.volvox.presentation;

import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;

public interface VolvoxPageConfigurator extends PageConfigurator {

    @Override
    default void configurePage(InitialPageSettings _settings) {
        _settings.addLink("shortcut icon", "favicon.ico");
    }
}
