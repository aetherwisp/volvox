package com.github.aetherwisp.volvox.application.security.auth;

import static java.lang.invoke.MethodHandles.lookup;
import static org.apache.logging.log4j.LogManager.getLogger;
import java.util.Objects;
import java.util.Optional;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.github.aetherwisp.volvox.domain.user.User;
import com.github.aetherwisp.volvox.domain.user.UserRepository;

/**
 * Class that actually performs authentication. The user information and password are obtained from
 * the received token and the unique authentication process is performed. Roles are also given here.
 */
@Component
public class VolvoxAuthenticationProvider implements AuthenticationProvider {
    //======================================================================
    // Fields
    private static final Logger logger = getLogger(lookup().lookupClass());
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    //======================================================================
    // Constructors
    @Autowired
    public VolvoxAuthenticationProvider(final PasswordEncoder _passwordEncoder, final UserRepository _userRepository) {
        this.passwordEncoder = Objects.requireNonNull(_passwordEncoder);
        this.userRepository = Objects.requireNonNull(_userRepository);
    }

    //======================================================================
    // Methods
    @Override
    public Authentication authenticate(Authentication _authentication) throws AuthenticationException {
        try {
            final String username = Optional.ofNullable(_authentication.getName())
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("username or password is incorrect."));
            final String password = Optional.ofNullable((String) _authentication.getCredentials())
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("username or password is incorrect."));

            //======================================================================
            // 認証
            final User user = this.userRepository.finder()
                .filterByName(username)
                .find()
                .stream()
                .findFirst()
                .orElse(null);
            if (null == user || !this.passwordEncoder.matches(password, user.getPassword())) {
                // ユーザ名またはパスワードが違う
                throw new BadCredentialsException("Username or password is invalid.");
            }
            if (!user.isAccountNonExpired()) {
                // アカウント期限切れ
                throw new AccountExpiredException("Account is expired.");
            }
            if (!user.isAccountNonLocked()) {
                // アカウントロック
                throw new LockedException("Account is locked.");
            }
            if (!user.isEnabled()) {
                // アカウント無効
                throw new DisabledException("Account is disabled.");
            }
            if (!user.isCredentialsNonExpired()) {
                // パスワード期限切れ
                throw new CredentialsExpiredException("Credential is expired.");

            }

            //======================================================================
            // 権限付与
            return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
        } catch (AuthenticationException e) {
            logger.warn(e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AuthenticationServiceException("Authentication request could not be processed due to a system problem.", e);
        }
    }

    /**
     * 指定されたトークンを処理すべきか否かを返します。
     * <p>
     * false を返すとフレームワークは別の認証プロバイダーを探そうとするので、これを利用して複数の認証方法を提供することもできます。
     */
    @Override
    public boolean supports(Class<?> _authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(_authentication);
    }

}
