package com.github.aetherwisp.volvox.application.security.auth;

import static com.github.aetherwisp.volvox.application.MessageKeys.Index.Login.ERROR_ACCOUNT_DISABLED;
import static com.github.aetherwisp.volvox.application.MessageKeys.Index.Login.ERROR_ACCOUNT_EXPIRED;
import static com.github.aetherwisp.volvox.application.MessageKeys.Index.Login.ERROR_ACCOUNT_LOCKED;
import static com.github.aetherwisp.volvox.application.MessageKeys.Index.Login.ERROR_BAD_CREDENTIALS;
import static com.github.aetherwisp.volvox.application.MessageKeys.Index.Login.ERROR_CREDENTIAL_EXPIRED;
import static com.github.aetherwisp.volvox.application.MessageKeys.Index.Login.ERROR_FAILED;
import static com.github.aetherwisp.volvox.application.SessionKeys.Index.Login.ERROR;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class VolvoxAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    //======================================================================
    // Fields
    private final String contentType;

    //======================================================================
    // Constants
    public VolvoxAuthenticationFailureHandler(final String _defaultTargetUrl) {
        this(_defaultTargetUrl, MediaType.TEXT_HTML_VALUE);
    }

    public VolvoxAuthenticationFailureHandler(final String _defaultTargetUrl, final String _contentType) {
        super(_defaultTargetUrl);
        this.contentType = Objects.requireNonNull(_contentType);
    }

    //======================================================================
    // Methods
    @Override
    public void onAuthenticationFailure(HttpServletRequest _request, HttpServletResponse _response, AuthenticationException _exception)
            throws IOException, ServletException {
        super.onAuthenticationFailure(_request, _response, _exception);
        _response.setContentType(this.contentType);
        _response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());

        final HttpSession session = _request.getSession();
        if (null != session) {
            if (_exception instanceof AuthenticationCredentialsNotFoundException) {
                session.setAttribute(ERROR, ERROR_BAD_CREDENTIALS);
            } else if (_exception instanceof BadCredentialsException) {
                session.setAttribute(ERROR, ERROR_BAD_CREDENTIALS);
            } else if (_exception instanceof AccountExpiredException) {
                session.setAttribute(ERROR, ERROR_ACCOUNT_EXPIRED);
            } else if (_exception instanceof LockedException) {
                session.setAttribute(ERROR, ERROR_ACCOUNT_LOCKED);
            } else if (_exception instanceof DisabledException) {
                session.setAttribute(ERROR, ERROR_ACCOUNT_DISABLED);
            } else if (_exception instanceof CredentialsExpiredException) {
                session.setAttribute(ERROR, ERROR_CREDENTIAL_EXPIRED);
            } else if (_exception instanceof AuthenticationServiceException) {
                session.setAttribute(ERROR, ERROR_FAILED);
            } else if (_exception instanceof AuthenticationException) {
                session.setAttribute(ERROR, ERROR_FAILED);
            } else {
                session.setAttribute(ERROR, ERROR_FAILED);
            }
        }

    }
}
