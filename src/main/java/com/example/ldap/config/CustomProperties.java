package com.example.ldap.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * https://stackoverflow.com/questions/48161636/spring-boot-dynamic-bean-creation-from-properties-file
 * https://www.mkyong.com/spring-boot/spring-boot-configurationproperties-example/
 */
@Component
@ConfigurationProperties(
        prefix = "ldap.pool"
)
public class CustomProperties {

    private final Map<String, String> properties = new HashMap<>();

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        AutowireCapableBeanFactory beanFactory = this.applicationContext.getAutowireCapableBeanFactory();
        // iterate over properties and register new beans
    }

}
