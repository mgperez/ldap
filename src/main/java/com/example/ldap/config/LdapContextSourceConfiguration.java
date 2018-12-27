package com.example.ldap.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.util.StringUtils;

import java.util.Hashtable;
import java.util.Map;

/**
 * https://www.logicbig.com/tutorials/spring-framework/spring-core/import-bean-registrar.html
 * https://dzone.com/articles/how-create-your-own-dynamic
 * http://www.javased.com/index.php?api=org.springframework.beans.MutablePropertyValues
 * https://www.programcreek.com/java-api-examples/?code=melthaw/spring-backend-boilerplate/spring-backend-boilerplate-master/menu/core/src/main/java/in/clouthink/daas/sbb/menu/annotation/EnableMenuImportSelector.java
 */
@Configuration
@Slf4j
public class LdapContextSourceConfiguration implements ImportBeanDefinitionRegistrar {

    //http://docs.oracle.com/javase/tutorial/jndi/newstuff/readtimeout.html
    // com.sun.jndi.ldap.LdapCtx
    // Timeout for socket connect
    public static final String CONNECT_TIMEOUT = "com.sun.jndi.ldap.connect.timeout";
    // Timeout for reading responses
    public static final String READ_TIMEOUT = "com.sun.jndi.ldap.read.timeout";

    private static final String ATT_BASE = "base";
    private static final String ATT_PASSWORD = "password";
    private static final String ATT_URL = "url";


    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {

        String ldapKey = "ldap1";//ldapData.getLdapId();
//        String readTimeout = poolingConfig.getReadTimeout();
//        String connectTimeout = poolingConfig.getConnectTimeout();

        MutablePropertyValues propMdo = parseLdapContextSource();//ldapData, false, readTimeout, connectTimeout);
        register(beanDefinitionRegistry, ldapKey, propMdo);

    }

    /**
     * https://www.programcreek.com/java-api-examples/?code=spring-projects/spring-ldap/spring-ldap-master/core/src/main/java/org/springframework/ldap/config/ContextSourceParser.java
     * https://www.logicbig.com/tutorials/spring-framework/spring-core/bean-definition.html
     * @param registry
     * @param suffix
     * @param mpv
     */
    private void register(BeanDefinitionRegistry registry, String suffix, MutablePropertyValues mpv){
        String name = "dataSource_" + suffix;

//        BeanDefinition bd = new RootBeanDefinition(LdapContextSource.class,
//                new ConstructorArgumentValues(), propMdo);

//        registry.registerBeanDefinition(name, bd);

//        GenericBeanDefinition bd = new GenericBeanDefinition();
//        bd.setBeanClass(LdapContextSource.class);
//        bd.setPropertyValues(mpv);
//
//        registry.registerBeanDefinition(name, bd);

//        BeanDefinitionBuilder beanBuilder = BeanDefinitionBuilder.rootBeanDefinition(LdapContextSource.class);
//        beanBuilder.getRawBeanDefinition().setPropertyValues(mpv);

//        registry.registerBeanDefinition(name, beanBuilder.getBeanDefinition());

        BeanDefinitionBuilder builder =
                BeanDefinitionBuilder.genericBeanDefinition(LdapContextSource.class);
        AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
        beanDefinition.setPropertyValues(mpv);

        registry.registerBeanDefinition(name, beanDefinition);



    }

    // https://www.programcreek.com/java-api-examples/?code=spring-projects/spring-ldap/spring-ldap-master/core/src/main/java/org/springframework/ldap/config/ContextSourceParser.java
    private MutablePropertyValues parseLdapContextSource() throws BeansException {//LdapData ldapData, boolean primary, String readTimeout, String connectTimeout) throws BeansException {
        String defaultUser = "cn=ldapAdmin";//ldapData.getUser();
        String defaultPassword = "password1";//ldapData.getPassword();
        String url = "ldap://localhost:3060";//ldapData.getUrim();
        String base = "dc=mycompany,dc=com";//ldapData.getBase(primary);
        String readTimeout = "5000";
        String connectTimeout = "5000";


        MutablePropertyValues propMdo = new MutablePropertyValues();

        boolean isSecured = url.startsWith("ldaps://");

        propMdo.add(ATT_URL, url);
        // Assert.hasText(url, "url attribute must be specified");

        if (StringUtils.hasText(base)) {
            propMdo.add(ATT_BASE, base);
        }

        propMdo.add("userDn", defaultUser);
        propMdo.add(ATT_PASSWORD, defaultPassword);

        /*
         * if (isSecured) { propMdo.add(ATT_PASSWORD, "wHhEMDPgCaF7qHtR6X4M"); } else {
         * propMdo.add(ATT_PASSWORD, defaultPassword); }
         */

        // Assert.hasText(defaultUser, "username attribute must be specified unless an
        // authentication-source-ref explicitly configured");
        // Assert.hasText(defaultPassword, "password attribute must be specified unless
        // an authentication-source-ref explicitly configured");

        propMdo.add("pooled", "false");

        // <entry key="java.naming.referral" value="follow" />

        // Explained at
        // http://docs.oracle.com/javase/jndi/tutorial/ldap/security/auth.html
        // baseEnv.put("java.naming.security.authentication", "simple");

        //PoolingPropertiesHelper poolingConfig = new PoolingPropertiesHelper();

        Map<String, Object> baseEnvPropsRef = getBaseEnv(readTimeout, connectTimeout, isSecured);
        if (baseEnvPropsRef != null && !baseEnvPropsRef.isEmpty()) {
            propMdo.add("baseEnvironmentProperties", baseEnvPropsRef);
        }

        return propMdo;
    }

    private Map<String, Object> getBaseEnv(String readTimeout, String connectTimeout, boolean isSecured) {
        //PoolingPropertiesHelper poolingConfig = new PoolingPropertiesHelper();
        //String readTimeout = poolingConfig.getReadTimeout();
        //String connectTimeout = poolingConfig.getConnectTimeout();

        // Place JNDI environment properties here
        Map<String, Object> baseEnvPropsRef = new Hashtable<String, Object>();

        // https://docs.oracle.com/javase/7/docs/technotes/guides/jndi/jndi-ldap.html#POOLPROP
        // If the LDAP provider cannot get a LDAP response within that period, it aborts the read attempt.
        // Three seconds is an eternity to users
        if (StringUtils.hasText(readTimeout)) {
            baseEnvPropsRef.put(READ_TIMEOUT, readTimeout);
            log.info("{} {}", READ_TIMEOUT, readTimeout);
        }

        //  If the LDAP provider cannot establish a connection within that period, it aborts the connection attempt.
        if (StringUtils.hasText(connectTimeout)) {
            baseEnvPropsRef.put(CONNECT_TIMEOUT, connectTimeout);
            log.info("{} {}", CONNECT_TIMEOUT, connectTimeout);
        }

        /*
         * String truststoreType = poolingConfig.getTrustStoreType(); String
         * trustStoreLocation = poolingConfig.getTrustStore(); String truststorePassword
         * = poolingConfig.getTrustStorePassword();
         */

/*
        if (isSecured) {// && StringUtils.hasText(trustStoreLocation)) {

            baseEnvPropsRef.put(javax.naming.Context.SECURITY_PROTOCOL, "ssl");
            baseEnvPropsRef.put(SECURITY_FACTORY_SOCKECT, MySSLSocketFactory.class.getName());

        }
*/

        return baseEnvPropsRef;
    }
}
