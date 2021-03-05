package com.github.aetherwisp.volvox.application.user;

import java.util.Objects;
import org.springframework.security.core.context.SecurityContextHolder;
import com.github.aetherwisp.volvox.domain.user.User;

public final class Users {
    private Users() {
        throw new UnsupportedOperationException();
    }

    //======================================================================
    // Methods
    /**
     * @return Current logged in user object.
     */
    public static User currentUser() {
        final Object principal = Objects.requireNonNull(SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal());
        if (principal instanceof User) {
            return (User) principal;
        } else {
            throw new IllegalStateException("Unknown user type.[" + principal.getClass()
                .getName());
        }
    }
}
