package com.example.ldap.data;

import org.springframework.ldap.core.LdapTemplate;

/**
 * https://nitinbksc.wordpress.com/2012/06/07/programmatic-way-of-ldap-transaction/
 */
public class LdapEntityDAO implements ILdapEntityDAO {

    private LdapTemplate getLdapTemplate() {
        return LdapContextHolder.getLdapTemplate();
    }

    public void createEntity(EntityObj entityObj) throws Exception {
        LdapTemplate vLdapTemplate = getLdapTemplate(); // Get the LdapTemplate
        //Ldap code goes here .....
    }
}
