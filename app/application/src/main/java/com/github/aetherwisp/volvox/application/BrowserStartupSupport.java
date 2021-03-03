package com.github.aetherwisp.volvox.application;

import static java.lang.invoke.MethodHandles.lookup;
import static org.apache.logging.log4j.LogManager.getLogger;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class BrowserStartupSupport implements ApplicationListener<WebServerInitializedEvent> {
    //======================================================================
    // Fields
    private static final Logger LOG = getLogger(lookup().lookupClass());

    private final boolean browserStartup;

    //======================================================================
    // Constructors
    @Autowired
    public BrowserStartupSupport(
            @Value("${" + PropertyKeys.BROWSER_STARTUP + ":false}") boolean _browserStartup) {
        this.browserStartup = _browserStartup;
    }

    //======================================================================
    // Methods
    @Override
    public void onApplicationEvent(WebServerInitializedEvent _event) {
        //======================================================================
        // ポート番号を取得
        final Integer port = Integer.valueOf(_event.getWebServer()
                .getPort());

        //======================================================================
        // コンテキストパスを取得
        final String contextPath = _event.getApplicationContext()
                .getBean(Environment.class)
                .getProperty("server.servlet.context-path");

        //======================================================================
        // ブラウザを自動起動
        try {
            final String url = String.format("http://localhost:%d/%s", port, contextPath);
            if (this.browserStartup) {
                final Desktop desktop = Desktop.getDesktop();
                desktop.browse(URI.create(url));
            } else {
                LOG.info("URL: {}", url);
                return;
            }
        } catch (IOException e) {
            // ブラウザの自動起動に失敗した場合はユーザに手動でブラウザを起動してもらう
            LOG.warn(e.getMessage());
        }
    }
}
