package com.github.aetherwisp.volvox.application.security;

import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.vaadin.flow.server.HandlerHelper;
import com.vaadin.flow.shared.ApplicationConstants;

public final class SecurityUtils {
    private SecurityUtils() {
        throw new UnsupportedOperationException("Util methods only.");
    }

    //======================================================================
    // Methods
    /**
     * リクエストが Vaadin の内部にあるかどうかを判別します。
     *
     * @param _request HTTP リクエスト
     * @return リクエストが Vaadin の内部にあるなら true、そうでないなら false
     */
    public static boolean isFrameworkInternalRequest(HttpServletRequest _request) {
        final String parameterValue = _request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
        return parameterValue != null && Stream.of(HandlerHelper.RequestType.values())
            .anyMatch(r -> r.getIdentifier()
                .equals(parameterValue));
    }

    /**
     * @return 現在のユーザーがログインしているなら true、そうでないなら false
     */
    public static boolean isUserLoggedIn() {
        final Authentication authentication = SecurityContextHolder.getContext()
            .getAuthentication();
        return null != authentication && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
}
