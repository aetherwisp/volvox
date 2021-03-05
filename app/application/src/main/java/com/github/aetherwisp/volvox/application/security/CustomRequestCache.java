package com.github.aetherwisp.volvox.application.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

public class CustomRequestCache extends HttpSessionRequestCache {

    /**
     * 認証されていないリクエストを保存して、ログイン後にアクセスしようとしたページにユーザーをリダイレクトできるようにします。
     */
    @Override
    public void saveRequest(HttpServletRequest _request, HttpServletResponse _response) {
        if (!SecurityUtils.isFrameworkInternalRequest(_request)) {
            super.saveRequest(_request, _response);
        }
    }
}
