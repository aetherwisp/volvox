package com.github.aetherwisp.volvox.domain.user;

import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import com.github.aetherwisp.volvox.domain.Builder;
import com.github.aetherwisp.volvox.domain.Entity;

public class Permission implements Entity<Permission>, GrantedAuthority {
    private static final long serialVersionUID = 1106231947656658809L;

    //======================================================================
    // Fields
    private final Integer id;

    private final String name;

    //======================================================================
    // Constructors
    private Permission(final PermissionBuilder _builder) {
        final PermissionBuilder builder = Objects.requireNonNull(_builder);
        this.id = Objects.requireNonNull(builder.id);
        this.name = Objects.requireNonNull(builder.name);
    }

    //======================================================================
    // Methods
    @Override
    public String getAuthority() {
        return this.name;
    }

    @Override
    public boolean sameAs(Permission _other) {
        return (null == _other) ? false : this.id.equals(_other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
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
        Permission other = (Permission) obj;
        return Objects.equals(id, other.id) && Objects.equals(name, other.name);
    }

    //======================================================================
    // Getters
    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    //======================================================================
    // Classes
    public static class PermissionBuilder implements Builder<Permission> {
        //======================================================================
        // Fields
        private Integer id;

        private String name;

        //======================================================================
        // Methods
        @Override
        public Permission build() {
            return new Permission(this);
        }

        //======================================================================
        // Getters
        public Integer getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        //======================================================================
        // Setters
        public PermissionBuilder setId(Integer _id) {
            this.id = _id;
            return this;
        }

        public PermissionBuilder setName(String _name) {
            this.name = _name;
            return this;
        }

    }
}
