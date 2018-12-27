package com.example.ldap.config;

import com.example.ldap.service.ClientBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * https://www.logicbig.com/tutorials/spring-framework/spring-core/import-bean-registrar.html
 */
@Configuration
@EnableLdapContextSources
public class MyConfig {

    @Bean
    ClientBean clientBean () {
        return new ClientBean();
    }
}
