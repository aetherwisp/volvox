package com.github.aetherwisp.volvox.presentation;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;

@Route("dashboard")
public class MainView extends VerticalLayout implements HasDynamicTitle {
    private static final long serialVersionUID = 1L;

    //======================================================================
    // Fields

    //======================================================================
    // Constructors
    public MainView() {}

    @Override
    public String getPageTitle() {
        return "Page Title";
    }

}
