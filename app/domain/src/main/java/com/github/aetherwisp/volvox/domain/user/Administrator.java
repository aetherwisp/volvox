package com.github.aetherwisp.volvox.domain.user;

import java.time.LocalDateTime;
import java.util.List;
import com.github.aetherwisp.volvox.domain.Entity;
import com.github.aetherwisp.volvox.domain.environment.Environments;

public final class Administrator extends AbstractUser<Administrator> implements Entity<Administrator> {
    private static final long serialVersionUID = 3656259410622304183L;

    //======================================================================
    // Constructors
    private Administrator(final AdministratorBuilder _builder) {
        super(_builder);
    }

    //======================================================================
    // Methods
    @Override
    public boolean sameAs(Administrator _other) {
        return null != _other
                ? this.getId()
                    .equals(_other.getId())
                : false;
    }

    //======================================================================
    // Classes
    public static class AdministratorBuilder extends AbstractUserBuilder<Administrator> {
        @Override
        public Administrator build() {
            return new Administrator(this);
        }

        //======================================================================
        // Getters
        /**
         * @return 常に false
         */
        @Override
        public boolean isLocked() {
            return false;
        }

        /**
         * @return システム日時の 100 年後
         */
        @Override
        public LocalDateTime getExpiredAt() {
            return Environments.utcDateTime()
                .plusYears(100L);
        }

        /**
         * @return 全ての権限のリスト
         */
        @Override
        public List<Permission> getPermissions() {
            // FIXME: 全ての権限を返す
            throw new UnsupportedOperationException("未実装！");
        }

        //======================================================================
        // Setters
        @Override
        public AdministratorBuilder setPermissions(List<Permission> _permissions) {
            // do nothing.
            return this;
        }
    }
}
