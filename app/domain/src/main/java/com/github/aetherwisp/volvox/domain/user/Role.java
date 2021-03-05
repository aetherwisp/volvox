package com.github.aetherwisp.volvox.domain.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import com.github.aetherwisp.volvox.domain.Builder;
import com.github.aetherwisp.volvox.domain.Entity;

public class Role implements Entity<Role> {

    //======================================================================
    // Fields
    private final Integer id;

    private final String name;

    private final List<Permission> permissions;

    //======================================================================
    // Constructors
    private Role(final RoleBuilder _builder) {
        final RoleBuilder builder = Objects.requireNonNull(_builder);
        this.id = Objects.requireNonNull(builder.id);
        this.name = Objects.requireNonNull(builder.name);
        this.permissions = Collections.unmodifiableList(new ArrayList<>(builder.permissions));
    }

    //======================================================================
    // Methods
    @Override
    public boolean sameAs(Role _other) {
        return (null == _other) ? false : this.id.equals(_other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, permissions);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Role other = (Role) obj;
        return Objects.equals(id, other.id) && Objects.equals(name, other.name) && Objects.equals(permissions, other.permissions);
    }

    //======================================================================
    // Getters
    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public List<Permission> getPermissions() {
        return this.permissions;
    }

    //======================================================================
    // Classes
    public static class RoleBuilder implements Builder<Role> {
        //======================================================================
        // Fields
        private Integer id;

        private String name;

        private final List<Permission> permissions = new ArrayList<>();

        //======================================================================
        // Methods
        @Override
        public Role build() {
            return new Role(this);
        }

        //======================================================================
        // Getters
        public Integer getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public List<Permission> getPermissions() {
            return this.permissions;
        }

        //======================================================================
        // Setters
        public RoleBuilder setId(Integer _id) {
            this.id = _id;
            return this;
        }

        public RoleBuilder setName(String _name) {
            this.name = _name;
            return this;
        }

        public RoleBuilder setPermissions(List<Permission> _permissions) {
            this.permissions.clear();
            if (null != _permissions) {
                this.permissions.addAll(_permissions);
            }
            return this;
        }
    }
}
