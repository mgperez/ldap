package com.example.ldap.data;

import org.springframework.ldap.core.LdapTemplate;

/**
 * https://nitinbksc.wordpress.com/2012/06/07/programmatic-way-of-ldap-transaction/
 */
public class LdapContextHolder {

    private static ThreadLocal<LdapTemplate> ldapTemplate = new ThreadLocal<LdapTemplate>();

    /**
     * @return the ldapTemplate
     */
    public static LdapTemplate getLdapTemplate() {
        return ldapTemplate.get();
    }

    /**
     * @param ldapTemplate the ldapTemplate to set
     */
    public static void setLdapTemplate(LdapTemplate ldapTemplate) {
        LdapContextHolder.ldapTemplate.set(ldapTemplate);
    }
}
