package com.github.aetherwisp.volvox.domain.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import com.github.aetherwisp.volvox.domain.Builder;
import com.github.aetherwisp.volvox.domain.environment.Environments;

public abstract class AbstractUser<U extends User> implements User {
    private static final long serialVersionUID = -7592274386037995146L;

    //======================================================================
    // Fields
    /** A number that uniquely identifies this user. */
    private final Integer id;

    /** The name of this user. */
    private final String name;

    /** {@code true} if this user is locked, {@code false} otherwise. */
    private final boolean locked;

    /** The expiration UTC date for this user. */
    private final LocalDateTime expiredAt;

    private final Password password;

    private List<Permission> permissions;

    //======================================================================
    // Constructors
    protected AbstractUser(final AbstractUserBuilder<U> _builder) {
        final AbstractUserBuilder<U> builder = Objects.requireNonNull(_builder);
        this.id = Objects.requireNonNull(builder.getId());
        this.name = Objects.requireNonNull(builder.getName());
        this.locked = builder.isLocked();
        this.expiredAt = Objects.requireNonNull(builder.getExpiredAt());
        this.password = Objects.requireNonNull(builder.getPassword());
        this.permissions = Collections.unmodifiableList(new ArrayList<>(builder.getPermissions()));
    }

    //======================================================================
    // Getters
    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isLocked() {
        return this.locked;
    }

    @Override
    public LocalDateTime getExpiredAt() {
        return this.expiredAt;
    }

    @Override
    public boolean isEnabled() {
        return this.isAccountNonExpired() && this.isAccountNonLocked() && this.isCredentialsNonExpired() && !this.isLocked();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.permissions;
    }

    @Override
    public String getPassword() {
        return this.password.getPassword();
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return Environments.utcDateTime()
            .isBefore(this.getExpiredAt());
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.isLocked();
    }

    /**
     * @return ユーザーの資格情報（パスワード）が有効期限内なら true、そうでないなら false
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return Environments.utcDateTime()
            .isBefore(this.password.getExpiredAt());
    }

    //======================================================================
    // Classes
    protected static abstract class AbstractUserBuilder<U extends User> implements Builder<U> {
        private Integer id;
        private String name;
        private boolean locked;
        private LocalDateTime expiredAt;
        private Password password;
        private final List<Permission> permissions = new ArrayList<>();

        //======================================================================
        // Getters
        public Integer getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public boolean isLocked() {
            return this.locked;
        }

        public LocalDateTime getExpiredAt() {
            return this.expiredAt;
        }

        public Password getPassword() {
            return this.password;
        }

        public List<Permission> getPermissions() {
            return this.permissions;
        }

        //======================================================================
        // Setters
        public AbstractUserBuilder<U> setId(Integer _id) {
            this.id = _id;
            return this;
        }

        public AbstractUserBuilder<U> setName(String _name) {
            this.name = _name;
            return this;
        }

        public AbstractUserBuilder<U> setLocked(boolean _locked) {
            this.locked = _locked;
            return this;
        }

        public AbstractUserBuilder<U> setExpiredAt(LocalDateTime _expiredAt) {
            this.expiredAt = _expiredAt;
            return this;
        }

        public AbstractUserBuilder<U> setPassword(Password _password) {
            this.password = _password;
            return this;
        }

        public AbstractUserBuilder<U> setPermissions(List<Permission> _permissions) {
            this.permissions.clear();
            if (null != _permissions) {
                this.permissions.addAll(_permissions);
            }
            return this;
        }
    }
}
