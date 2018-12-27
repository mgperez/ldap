package com.example.ldap.controller;

import com.example.ldap.service.ClientBean;
import com.example.ldap.service.UpdateRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MyController {

//    @Autowired
//    private List<LdapContextSource> sources;

    @Autowired @Qualifier("dataSource_ldap1")
    private LdapContextSource source1;

    @Autowired @Qualifier("contextSourceOneTarget")
    private LdapContextSource sourceOneTarget;

    @Autowired
    private ClientBean clientBean;

    @Autowired
    private UpdateRepositories updateRepositories;

    /**
     * http://localhost:8080/spring-ldap-rest/sources
     * @return
     */
    @RequestMapping("/sources")
    public String main(){
        updateRepositories.updateAllRepositories();
//        return service1.getName();
        String userDn = sourceOneTarget.getUserDn();
        return clientBean.doSomething();
    }
}
