package com.example.ldap.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
@Import(SampleServiceConfiguration.class)
public @interface EnableSampleServices {}