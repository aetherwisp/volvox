package com.github.aetherwisp.volvox.presentation.index;

import static java.lang.invoke.MethodHandles.lookup;
import static org.apache.logging.log4j.LogManager.getLogger;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.github.aetherwisp.volvox.presentation.PresentationConfiguration;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;

@Route("")
public class IndexView extends VerticalLayout implements HasDynamicTitle, BeforeEnterObserver {
    private static final long serialVersionUID = 1L;

    //======================================================================
    // Fields
    private static final Logger logger = getLogger(lookup().lookupClass());

    private final String title;

    private final LoginForm login = new LoginForm();

    //======================================================================
    // Constructors
    @Autowired
    public IndexView(@Value("${" + PresentationConfiguration.PREFIX + ".title}") final String _title) {
        this.title = _title;

        this.addClassName("login-view");
        this.setSizeFull();
        this.setAlignItems(Alignment.CENTER);
        this.setJustifyContentMode(JustifyContentMode.CENTER);

        this.login.setAction("login");

        this.add(new H1(this.title), this.login);
    }

    //======================================================================
    // Methods
    @Override
    public void beforeEnter(BeforeEnterEvent _event) {
        if (_event.getLocation()
            .getQueryParameters()
            .getParameters()
            .containsKey("error")) {
            this.login.setError(true);
        }
    }

    @Override
    public String getPageTitle() {
        return "Login | " + this.title;
    }
}
