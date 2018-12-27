package com.example.ldap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ldap.core.support.LdapContextSource;

/**
 * https://www.logicbig.com/tutorials/spring-framework/spring-core/import-bean-registrar.html
 */
public class ClientBean {

    @Autowired @Qualifier("dataSource_ldap1")
    private LdapContextSource source1;

    public String doSomething () {
//        appBean.process();
        return source1.getUserDn();
    }

}
