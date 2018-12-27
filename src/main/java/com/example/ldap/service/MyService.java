package com.example.ldap.service;

import com.example.ldap.data.LdapContextHolder;
import com.example.ldap.data.EntityObj;
import com.example.ldap.data.ILdapEntityDAO;
import com.example.ldap.data.LdapEntityDAO;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.transaction.compensating.manager.ContextSourceTransactionManager;
import org.springframework.ldap.transaction.compensating.manager.TransactionAwareContextSourceProxy;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * https://nitinbksc.wordpress.com/2012/06/07/programmatic-way-of-ldap-transaction/
 */
public class MyService {

    private ILdapEntityDAO entityDAO = new LdapEntityDAO();

    private LdapContextSource getLdapContext() throws Exception {
        try {
            LdapContextSource vContext = new LdapContextSource();
            vContext.setUrl("ldap://localhost:3060"); // LDAP Url
            vContext.setBase("dc=mycompany,dc=com"); //Base directory
            vContext.setUserDn("cn=ldapAdmin"); // User to connect to LDAP
            vContext.setPassword("password1"); // User password
            vContext.afterPropertiesSet();

            return vContext;
        } catch (Exception eX) {
            throw eX;
        }
    }

    public void createEntity(EntityObj entityObj) throws Exception {
        ContextSourceTransactionManager txManager = new ContextSourceTransactionManager();
        LdapContextSource ldapContext = getLdapContext();
        txManager.setContextSource(ldapContext);
        TransactionAwareContextSourceProxy contextProxy = new TransactionAwareContextSourceProxy(ldapContext);
        LdapTemplate ldapTemplate = new LdapTemplate(contextProxy);

        LdapContextHolder.setLdapTemplate(ldapTemplate); // Set the LdapTemplate

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus status = txManager.getTransaction(def);

        try {
            entityDAO.createEntity (entityObj);
            txManager.commit(status);
        } catch (Exception eX) {
            try {
                txManager.rollback(status);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            throw eX;
        } finally {
            txManager = null;
        }
    }
}
