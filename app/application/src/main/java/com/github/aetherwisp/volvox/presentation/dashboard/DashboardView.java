package com.github.aetherwisp.volvox.presentation.dashboard;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.github.aetherwisp.volvox.domain.user.User;
import com.github.aetherwisp.volvox.domain.user.UserRepository;
import com.github.aetherwisp.volvox.domain.util.Strings;
import com.github.aetherwisp.volvox.presentation.PresentationConfiguration;
import com.github.aetherwisp.volvox.presentation.VolvoxPageConfigurator;
import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;

@Route("dashboard")
public class DashboardView extends VerticalLayout implements HasDynamicTitle, VolvoxPageConfigurator {
    private static final long serialVersionUID = 1L;

    //======================================================================
    // Fields
    private final String title;

    //    private final Grid<User> grid = new Grid<>(User.class);
    private final Grid<User> grid;

    private final TextField filterText;

    private final UserRepository userRepository;

    //======================================================================
    // Constructors
    @Autowired
    public DashboardView(@Value("${" + PresentationConfiguration.PREFIX + ".title}") final String _title,
            final UserRepository _userRepository) {
        this.title = Objects.requireNonNull(_title);
        this.userRepository = Objects.requireNonNull(_userRepository);

        this.grid = new GridBuilder<>(User.class).addClassName("user-grid")
            .setSizeFull()
            .setColumns("id", "name", "locked", "expiredAt")
            .setItems(this.userRepository.finder()
                .find())
            .consumeColumns(col -> col.setAutoWidth(true))
            .build();

        this.filterText = TextFieldBuilder.newBuilder()
            .setPlaceholder("Filter by name...")
            .setClearButtonVisible(true)
            .setValueChangeMode(ValueChangeMode.LAZY)
            .addValueChangeListener(e -> {
                if (Strings.isNullOrEmpty(e.getValue())) {
                    this.grid.setItems(this.userRepository.finder()
                        .find());
                } else {
                    this.grid.setItems(this.userRepository.finder()
                        .filterByName(e.getValue())
                        .find());
                }
            })
            .build();

        this.addClassName("list-view");
        this.setSizeFull();

        this.add(this.filterText, this.grid);
    }

    @Override
    public String getPageTitle() {
        return "Dashboard | " + this.title;
    }

    private static class TextFieldBuilder {
        //======================================================================
        // Fields
        private final TextField field;

        //======================================================================
        // Constructors
        private TextFieldBuilder() {
            this.field = new TextField();
        }

        //======================================================================
        // Methods
        public static TextFieldBuilder newBuilder() {
            return new TextFieldBuilder();
        }

        public TextField build() {
            return this.field;
        }

        public TextFieldBuilder setPlaceholder(String _placeholder) {
            this.field.setPlaceholder(_placeholder);
            return this;
        }

        public TextFieldBuilder setClearButtonVisible(boolean _clearButtonVisible) {
            this.field.setClearButtonVisible(_clearButtonVisible);
            return this;
        }

        public TextFieldBuilder setValueChangeMode(ValueChangeMode _valueChangeMode) {
            this.field.setValueChangeMode(_valueChangeMode);
            return this;
        }

        public TextFieldBuilder addValueChangeListener(
                HasValue.ValueChangeListener<? super ComponentValueChangeEvent<TextField, String>> _listener) {
            this.field.addValueChangeListener(_listener);
            return this;
        }
    }

    private static class GridBuilder<T> {
        private final Grid<T> grid;

        public Grid<T> build() {
            return this.grid;
        }

        public GridBuilder(Class<T> _beanType) {
            grid = new Grid<>(_beanType);
        }

        public GridBuilder<T> addClassName(String _className) {
            this.grid.addClassName(_className);
            return this;
        }

        public GridBuilder<T> setSizeFull() {
            this.grid.setSizeFull();
            return this;
        }

        public GridBuilder<T> setColumns(String... _propertyNames) {
            this.grid.setColumns(_propertyNames);
            return this;
        }

        public GridBuilder<T> setItems(Collection<T> _items) {
            this.grid.setItems(_items);
            return this;
        }

        public GridBuilder<T> consumeColumns(Consumer<Grid.Column<T>> _action) {
            this.grid.getColumns()
                .forEach(_action);
            return this;
        }
    }
}
