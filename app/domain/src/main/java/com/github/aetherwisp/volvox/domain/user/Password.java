package com.github.aetherwisp.volvox.domain.user;

import java.time.LocalDateTime;
import java.util.Objects;
import com.github.aetherwisp.volvox.domain.Builder;
import com.github.aetherwisp.volvox.domain.Entity.Name;

public class Password {
    public static enum Property implements Name {
        ID("id"),
        USER_ID("userId", "user_id", "user-id"),
        PASSWORD("password"),
        EXPIRED_AT("expiredAt", "expired_at", "expired-at");


        private final String camelCase;
        private final String snakeCase;
        private final String kebabCase;

        private Property(String _camelCase) {
            this.camelCase = _camelCase;
            this.snakeCase = _camelCase;
            this.kebabCase = _camelCase;
        }

        private Property(String _camelCase, String _snakeCase, String _kebabCase) {
            this.camelCase = _camelCase;
            this.snakeCase = _snakeCase;
            this.kebabCase = _kebabCase;
        }

        @Override
        public String camelCase() {
            return this.camelCase;
        }

        @Override
        public String snakeCase() {
            return this.snakeCase;
        }

        @Override
        public String kebabCase() {
            return this.kebabCase;
        }
    }

    //======================================================================
    // Fields
    /** 【パスワード ID】パスワードの識別子 */
    private final Integer id;

    /** 【ユーザ ID】パスワードを所有するユーザの ID */
    private final Integer userId;

    /** 【パスワード】パスワード文字列 */
    private final String password;

    /** 【有効期限】パスワードの有効期限 */
    private final LocalDateTime expiredAt;

    //======================================================================
    // Constructors
    private Password(final PasswordBuilder _builder) {
        final PasswordBuilder builder = Objects.requireNonNull(_builder);
        this.id = Objects.requireNonNull(builder.id);
        this.userId = Objects.requireNonNull(builder.userId);
        this.password = Objects.requireNonNull(builder.password);
        this.expiredAt = Objects.requireNonNull(builder.expiredAt);
    }

    //======================================================================
    // Getters
    public Integer getId() {
        return this.id;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public String getPassword() {
        return this.password;
    }

    public LocalDateTime getExpiredAt() {
        return this.expiredAt;
    }

    //======================================================================
    // Classes
    public static class PasswordBuilder implements Builder<Password> {
        private Integer id;
        private Integer userId;
        private String password;
        private LocalDateTime expiredAt;

        //======================================================================
        // Methods
        @Override
        public Password build() {
            return new Password(this);
        }

        //======================================================================
        // Getters
        public Integer getId() {
            return this.id;
        }

        public Integer getUserId() {
            return this.userId;
        }

        public String getPassword() {
            return this.password;
        }

        public LocalDateTime getExpiredAt() {
            return this.expiredAt;
        }

        //======================================================================
        // Setters
        public void setId(Integer _id) {
            this.id = _id;
        }

        public void setUserId(Integer _userId) {
            this.userId = _userId;
        }

        public void setPassword(String _password) {
            this.password = _password;
        }

        public void setExpiredAt(LocalDateTime _expiredAt) {
            this.expiredAt = _expiredAt;
        }

    }
}
