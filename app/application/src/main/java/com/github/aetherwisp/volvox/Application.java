package com.github.aetherwisp.volvox;

import java.io.IOException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
public class Application extends SpringBootServletInitializer {
    static {
        System.setProperty("java.awt.headless", "false");
    }

    //======================================================================
    // Methods
    public static void main(String[] _args) {
        SpringApplication.run(Application.class, _args);
    }

    //======================================================================
    // Components
    @Bean
    public MessageSource messageSource() throws IOException {
        return new ResourceBundleMessageSource();
    }
}
