package com.example.ldap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * https://github.com/wmfairuz/spring-ldap-user-admin/blob/master/src/main/java/sample/UserApplication.java
 */
@SpringBootApplication
@EnableConfigurationProperties
//@EnableLdapRepositories("sample.domain")
@Slf4j
public class LdapApplication {

    public static void main(String[] args) {
        SpringApplication.run(LdapApplication.class, args);
        log.info("testing logging with lombok");
    }

}

