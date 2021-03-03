package com.github.aetherwisp.volvox.domain.context;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

/**
 * YAML implementation of {@code PropertySourceFactory}.
 */
public class YamlPropertySourceFactory implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String _name, EncodedResource _resource) throws IOException {

        final Resource yaml = _resource.getResource();
        if (null == yaml) {
            throw new FileNotFoundException("Resource is null.");
        } else if (!yaml.exists()) {
            throw new FileNotFoundException(yaml.getURL()
                    .toString());
        }

        final YamlPropertiesFactoryBean bean = new YamlPropertiesFactoryBean();
        bean.setResources(yaml);
        return new PropertiesPropertySource(Optional.ofNullable(yaml.getFilename())
                .orElseThrow(),
                Optional.ofNullable(bean.getObject())
                        .orElse(new Properties()));
    }

}
