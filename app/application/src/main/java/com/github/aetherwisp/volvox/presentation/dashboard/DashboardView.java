package com.github.aetherwisp.volvox.presentation.dashboard;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;

@Route("dashboard")
public class DashboardView extends VerticalLayout implements HasDynamicTitle {
    private static final long serialVersionUID = 1L;

    //======================================================================
    // Fields

    //======================================================================
    // Constructors
    public DashboardView() {}

    @Override
    public String getPageTitle() {
        return "Page Title";
    }

}
