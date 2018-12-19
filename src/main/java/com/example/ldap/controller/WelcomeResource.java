package com.example.ldap.controller;

import com.example.ldap.config.BasicConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * http://www.springboottutorial.com/spring-boot-application-configuration
 */
@Slf4j
@RestController
public class WelcomeResource {

    @Value("${welcome.message}")
    private String welcomeMessage;

    @Autowired
    private BasicConfiguration configuration;

    @RequestMapping("/dynamic-configuration")
    public Map dynamicConfiguration() {
        // Not the best practice to use a map to store differnt types!
        Map map = new HashMap();
        map.put("message", configuration.getMessage());
        map.put("number", configuration.getNumber());
        map.put("key", configuration.isValue());
        return map;
    }

    /**
     * http://localhost:9998/welcome
     * @return
     */
    @GetMapping("/welcome")
    public String retrieveWelcomeMessage() {
        // Complex Method
        return welcomeMessage;
    }
}