package com.example.ldap.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
@Import(LdapContextSourceConfiguration.class)
public @interface EnableLdapContextSources {}