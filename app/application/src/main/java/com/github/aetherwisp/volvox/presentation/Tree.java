package com.github.aetherwisp.volvox.presentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Tree {
    //======================================================================
    // Fields
    private final String name;

    private final String value;

    private List<Tree> children;

    //======================================================================
    // Constructors
    public Tree(final String _name) {
        this(_name, null, new ArrayList<>());
    }

    public Tree(final String _name, final String _value) {
        this(_name, _value, new ArrayList<>());
    }

    public Tree(final String _name, final String _value, final List<Tree> _children) {
        this.name = Objects.requireNonNull(_name);
        this.value = _value;
        this.children = Collections.unmodifiableList(new ArrayList<>(Optional.ofNullable(_children)
            .orElse(new ArrayList<>())));
    }

    public Tree(final String _name, final String _value, final Tree... _children) {
        this(_name, _value, Arrays.asList(_children));
    }

    private Tree(final List<Tree> _children) {
        this.name = null;
        this.value = null;
        this.children = Collections.unmodifiableList(new ArrayList<>(Optional.ofNullable(_children)
            .orElse(new ArrayList<>())));
    }

    //======================================================================
    // Methods
    public static Tree root(final Tree... _children) {
        return new Tree(Arrays.asList(_children));
    }

    public static Tree root(final List<Tree> _children) {
        return new Tree(_children);
    }

    //======================================================================
    // Getters
    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    public List<Tree> getChildren() {
        return this.children;
    }

}
