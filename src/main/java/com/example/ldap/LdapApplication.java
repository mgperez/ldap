package com.example.ldap;

import com.example.ldap.config.EnableLdapContextSources;
import com.example.ldap.config.EnableSampleServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * https://github.com/wmfairuz/spring-ldap-user-admin/blob/master/src/main/java/sample/UserApplication.java
 * https://www.baeldung.com/spring-data-ldap
 *
 * @EnableLdapRepositories hints Spring to scan the given package for interfaces marked as @Repository.
 *
 */
@SpringBootApplication
@EnableConfigurationProperties
//@EnableLdapRepositories("sample.domain")
@EnableSampleServices
@Slf4j
public class LdapApplication {

    public static void main(String[] args) {
        SpringApplication.run(LdapApplication.class, args);
        log.info("testing logging with lombok");
    }

}

