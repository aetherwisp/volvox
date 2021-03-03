package com.github.aetherwisp.volvox.application.error;

import static java.lang.invoke.MethodHandles.lookup;
import static org.apache.logging.log4j.LogManager.getLogger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.error.ErrorAttributeOptions.Include;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class VolvoxErrorController implements ErrorController {
    //======================================================================
    // Fields
    private static final Logger LOG = getLogger(lookup().lookupClass());

    private final String errorPath;

    private final ErrorAttributes errorAttributes;

    //======================================================================
    // Constructors
    @Autowired
    public VolvoxErrorController(
            @Value("${server.error.path:${error.path:/error}}") String _errorPath,
            ErrorAttributes _errorAttributes) {
        this.errorPath = _errorPath;
        this.errorAttributes = _errorAttributes;
    }

    //======================================================================
    // Methods
    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletRequest _request, HttpServletResponse _response) {

        final WebRequest webRequest = new ServletWebRequest(_request);
        LOG.debug(this.errorAttributes.getErrorAttributes(webRequest,
                ErrorAttributeOptions.of(Include.BINDING_ERRORS, Include.EXCEPTION)));
        LOG.error(this.errorAttributes.getError(webRequest));

        // TODO: View を返すエンドポイントはまだ無いので何もしない

        return new ModelAndView("error");
    }

    /**
     * レスポンスの Content-Type が application/json で、 例外が発生するか HTTP ステータスコードが 400 以上のレスポンスをハンドリングします。
     *
     * @param _request HTTP リクエスト
     * @param _response HTTP レスポンス
     * @return JSON または null
     */
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public String errorJson(HttpServletRequest _request, HttpServletResponse _response) {

        LOG.debug(this.errorAttributes.getErrorAttributes(new ServletWebRequest(_request),
                ErrorAttributeOptions.defaults()));

        // FIXME: エラー時のレスポンス仕様を決める

        return null;
    }

    @Override
    public String getErrorPath() {
        return this.errorPath;
    }
}
