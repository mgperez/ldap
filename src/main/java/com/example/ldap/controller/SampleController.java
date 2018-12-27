package com.example.ldap.controller;

import com.example.ldap.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * https://gist.github.com/ehdez73/b9ac35bf8d230c769309
 */
@RestController
public class SampleController {

    @Autowired
    private List<SampleService> services;

    @Autowired @Qualifier("s1")
    private SampleService service1;

    @Autowired @Qualifier("s2")
    private SampleService service2;

    @RequestMapping("/services")
    public String main(){
        return services.stream()
                .map( sampleService -> sampleService.getName())
                .collect(Collectors.joining(", "));
    }

    @RequestMapping("/s1")
    public String service1(){
        return service1.getName();
    }

    @RequestMapping("/s2")
    public String service2(){
        return service2.getName();
    }
}
