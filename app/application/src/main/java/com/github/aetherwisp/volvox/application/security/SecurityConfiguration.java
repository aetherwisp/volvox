package com.github.aetherwisp.volvox.application.security;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.github.aetherwisp.volvox.application.security.auth.VolvoxAuthenticationFailureHandler;
import com.github.aetherwisp.volvox.application.security.auth.VolvoxAuthenticationProvider;
import com.github.aetherwisp.volvox.application.security.auth.VolvoxAuthenticationSuccessHandler;
import com.github.aetherwisp.volvox.domain.user.UserRepository;

@Configuration
@EnableWebSecurity // Spring Security を有効化
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    //======================================================================
    // Constants
    private static final String LOGIN_PROCESSING_URL = "/login";
    private static final String LOGIN_FAILURE_URL = "/login?error";
    private static final String LOGIN_URL = "/login";
    private static final String LOGOUT_SUCCESS_URL = "/login";

    //======================================================================
    // Fields
    private final UserRepository userRepository;

    //======================================================================
    // Constructors
    @Autowired
    public SecurityConfiguration(final UserRepository _userRepository) {
        this.userRepository = Objects.requireNonNull(_userRepository);
    }

    //======================================================================
    // Methods
    @Override
    protected void configure(HttpSecurity _http) throws Exception {
        // Vaadin には既に CSRF 保護があるため、Spring Security の CSRF 保護を無効にする
        _http.csrf()
            .disable();

        // 不正な要求を追跡し、ログイン後にユーザが適切にリダイレクトする
        _http.requestCache()
            .requestCache(new CustomRequestCache());

        // Vaadin フレームワークからの全ての内部トラフィックを許可する
        _http.authorizeRequests()
            .requestMatchers(SecurityUtils::isFrameworkInternalRequest)
            .permitAll()
            .anyRequest()
            .authenticated();

        // フォームベースのログインを有効にする
        _http.formLogin()
            .loginPage("/")
            .loginProcessingUrl("/login")
            .failureUrl("/login?error")
            .successHandler(new VolvoxAuthenticationSuccessHandler("/dashboard/"))
            .failureHandler(new VolvoxAuthenticationFailureHandler("/index"))
            .permitAll();

        _http.logout()
            .logoutSuccessUrl("/");
    }

    /**
     * Vaadin フレームワーク通信と静的アセットを Spring Security から除外します。
     */
    @Override
    public void configure(WebSecurity _web) throws Exception {
        _web.ignoring()
            .antMatchers("/VAADIN/**", "/favicon.ico", "/favicon.png", "/robots.txt", "/manifest.webmanifest", "/sw.js",
                    "/offline.html", "/icons/**", "/images/**", "/styles/**", "/h2-console/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(this.authenticationProvider());
    }

    //======================================================================
    // Components
    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new VolvoxAuthenticationProvider(this.passwordEncoder(), this.userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
