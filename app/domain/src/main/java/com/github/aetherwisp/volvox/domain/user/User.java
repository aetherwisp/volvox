package com.github.aetherwisp.volvox.domain.user;

import java.time.LocalDateTime;
import org.springframework.security.core.userdetails.UserDetails;

public interface User extends UserDetails {
    Integer getId();

    String getName();

    boolean isLocked();

    LocalDateTime getExpiredAt();

}
