package com.github.aetherwisp.volvox.presentation.dashboard;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.github.aetherwisp.volvox.domain.user.User;
import com.github.aetherwisp.volvox.domain.user.UserRepository;
import com.github.aetherwisp.volvox.presentation.PresentationConfiguration;
import com.github.aetherwisp.volvox.presentation.VolvoxPageConfigurator;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;

@Route("dashboard")
public class DashboardView extends VerticalLayout implements HasDynamicTitle, VolvoxPageConfigurator {
    private static final long serialVersionUID = 1L;

    //======================================================================
    // Fields
    private final String title;

    private final Grid<User> grid = new Grid<>(User.class);

    private final UserRepository userRepository;

    //======================================================================
    // Constructors
    @Autowired
    public DashboardView(@Value("${" + PresentationConfiguration.PREFIX + ".title}") final String _title,
            final UserRepository _userRepository) {
        this.title = Objects.requireNonNull(_title);
        this.userRepository = Objects.requireNonNull(_userRepository);

        this.addClassName("list-view");
        this.setSizeFull();
        this.configureGrid();

        this.add(this.grid);
    }

    @Override
    public String getPageTitle() {
        return "Dashboard | " + this.title;
    }

    private void configureGrid() {
        this.grid.addClassName("user-grid");
        this.grid.setSizeFull();
        this.grid.setColumns("id", "name", "locked", "expiredAt");

        // データバインディング
        this.grid.setItems(this.userRepository.finder()
            .find());

        // 最も広いコンテンツに適合するのに十分な幅の列を作成する
        this.grid.getColumns()
            .forEach(col -> col.setAutoWidth(true));
    }
}
