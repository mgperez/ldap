package com.example.ldap.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ldap.LdapProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.pool.factory.PoolingContextSource;
import org.springframework.ldap.pool.validation.DefaultDirContextValidator;
import org.springframework.ldap.transaction.compensating.manager.ContextSourceTransactionManager;
import org.springframework.ldap.transaction.compensating.manager.TransactionAwareContextSourceProxy;
import org.springframework.ldap.transaction.compensating.support.DefaultTempEntryRenamingStrategy;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * https://www.adictosaltrabajo.com/2014/04/09/spring-ldap-template-transactions-connection-pool/
 * https://docs.spring.io/spring-ldap/docs/2.3.1.RELEASE/reference/#pooling
 * https://github.com/eugenp/tutorials/blob/master/spring-ldap/src/main/java/com/baeldung/ldap/javaconfig/AppConfig.java
 *
 * https://www.programcreek.com/java-api-examples/?code=cuba-platform/cuba/cuba-master/modules/rest-api/src/com/haulmont/restapi/ldap/LdapAuthController.java
 * https://stackoverflow.com/questions/5486378/how-do-i-combine-a-transactionawarecontextsourceproxy-with-a-poolingcontextsourc
 *
 * https://dzone.com/articles/spring-transaction-management
 * <tx:annotation-driven transaction-manager="txManager"/>
 * @EnableTransactionManagement Enables Spring’s annotation driven transaction management
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
public class ContextSourceConfig {

    public static final String CONNECT_TIMEOUT_ENV = "com.sun.jndi.ldap.connect.timeout";
    public static final String READ_TIMEOUT_ENV = "com.sun.jndi.ldap.read.timeout";

    @Autowired
    private Environment environment;


    @Bean(name="ldapProperties2")
    @ConfigurationProperties(prefix="spring.ldap2")
    public LdapProperties ldapProperties() {
        return new LdapProperties();
    }


    /*
    <bean id="contextSource" class="org.springframework.ldap.transaction.compensating.manager.TransactionAwareContextSourceProxy">
	      	<constructor-arg ref="contextSourceTarget" />
	   	</bean>

<bean id="contextSource" class="org.springframework.ldap.pool.factory.PoolingContextSource">
   <property name="contextSource" ref="contextSourceTarget" />
</bean>

    <ldap:context-source
        username="cn=Manager" password="secret" url="ldap://localhost:389" >
        <ldap:pooling
            test-on-borrow="true"  --> las instancias de DirContext deben ser validadas antes de usarse por el pool.
            test-while-idle="true" /> --> las instancias de DirContext en espera en el pool deben ser validadas con una frecuencia especificada. Los objetos que no pasen la validación serán elimiados del pool.
    </ldap:context-source>

<bean id="pooledContextSource" class="org.springframework.ldap.pool.factory.PoolingContextSource">
    <property name="contextSource" ref="contextSourceTarget"/>
    <property name="dirContextValidator" ref="dirContextValidator"/>
    <property name="testOnBorrow" value="true"/>
    <property name="testWhileIdle" value="true"/>
    <property name="minIdle" value="${ldap.minIdle}"/>
    <property name="maxIdle" value="${ldap.maxIdle}"/>
    <property name="maxActive" value="${ldap.maxActive}"/>
    <property name="maxTotal" value="${ldap.maxTotal}"/>
    <property name="maxWait" value="${ldap.maxWait}"/>
</bean>

*/


    @Bean
    public DefaultDirContextValidator dirContextValidator (){
        return new DefaultDirContextValidator();
    }

    @Bean
    public ContextSource poolingLdapContextSource() {

        PoolingContextSource poolingContextSource = new PoolingContextSource();
//        poolingContextSource.setDirContextValidator(new DefaultDirContextValidator());
        poolingContextSource.setDirContextValidator(dirContextValidator());
        poolingContextSource.setContextSource(contextSourceTarget());
        poolingContextSource.setTestOnBorrow(true);
        poolingContextSource.setTestWhileIdle(true);

/*
     <property name="timeBetweenEvictionRunsMillis" value="${ldapPool.timeBetweenEvictionRunsMillis}" />
     <property name="minEvictableIdleTimeMillis" value="${ldapPool.minEvictableIdleTimeMillis}" />
     <property name="maxActive" value="${ldapPool.maxActiveConnections}" />
     <!-- Open up "minIdle" connections when first request comes in -->
     <property name="minIdle" value="${ldapPool.initialPoolSize}" />
     <!-- maxIdle: set this, or it uses the default of 8 -->
     <property name="maxIdle" value="${ldapPool.maxActiveConnections}" />
     <!-- Round robin -->
     <property name="lifo" value="false" />
*/

//        TransactionAwareContextSourceProxy proxy = new TransactionAwareContextSourceProxy(poolingContextSource);

        return poolingContextSource;
//        return proxy;
    }

/*
<bean id="contextSourceTarget" class="org.springframework.ldap.core.support.LdapContextSource">
   <property name="url" value="ldap://localhost:389" />
   <property name="base" value="dc=example,dc=com" />
   <property name="userDn" value="cn=Manager" />
   <property name="password" value="secret" />
   <property name="pooled" value="false"/>
</bean>
*/

    @Bean
    @ConfigurationProperties(prefix="particulares.ldap.contextsource")
    public LdapContextSource contextSourceTarget() {
        LdapContextSource contextSource = new LdapContextSource();
//        contextSource.setAnonymousReadOnly(true);
//        contextSource.setUrl("ldap://" + ldapHost + ":" + ldapPort);
//        s.setUrl(String.format("ldap://localhost:%d", port));
//        contextSource.setUrls(ldapUrls.toArray(new String[]{}));
//        contextSource.setUrl("ldap://localhost:3060"); // LDAP Url

//        <property name="base" value="${ldapConfig.base}" />
//        contextSource.setBase("dc=mycompany,dc=com"); //Base directory

//        contextSource.setUserDn("cn=ldapAdmin"); // User to connect to LDAP //ldapUsername);
//        contextSource.setPassword("password1"); // User password //ldapPassword);

//        https://github.com/SNCF-SIV/spring-security-rest-jwt-ldap/blob/master/src/main/java/com/sncf/siv/poc/security/config/WebSecurityConfiguration.java
//        String secret = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("secret.key"), Charset.defaultCharset());

//        contextSource.setDirObjectFactory(null);

//        set it to false (which is the recommended value when using spring-ldap) then the AbstractContextSource tells you he will not use the LDAP pooling
//        contextSource.setPooled(false);


/*
        Map<String, Object> baseEnv = new HashMap<String, Object>();
        baseEnv.put(CONNECT_TIMEOUT_ENV, "" + authCfg.getConnectTimeout());
        baseEnv.put(READ_TIMEOUT_ENV, "" + authCfg.getReadTimeout());
        ldapCtx.setBaseEnvironmentProperties(baseEnv);

        Hashtable<String, Object> env = new Hashtable<String, Object>();
        env.put(Context.REFERRAL, "follow");
        cs.setBaseEnvironmentProperties(env);
        cs.setPassword(Config.getLdapPassword());
*/

        return contextSource;
    }


    @Bean
    public ContextSource transactionAwareContextSourceProxy(ContextSource poolingLdapContextSource) {
        return new TransactionAwareContextSourceProxy(poolingLdapContextSource);
    }


/*
    <bean id="ldapTemplate" class="org.springframework.ldap.core.LdapTemplate">
	      	<constructor-arg ref="contextSource" />
	   	</bean>
*/

//    @Bean(name = "ldapTemplateOne")
    @Bean
    public LdapTemplate ldapTemplate(ContextSource transactionAwareContextSourceProxy) {
        LdapTemplate ldapTemplate = new LdapTemplate(transactionAwareContextSourceProxy);
//        ldapTemplate.setIgnorePartialResultException(true);
        return ldapTemplate;
    }


/*
    <bean id="transactionManager" class="org.springframework.ldap.transaction.compensating.manager.ContextSourceTransactionManager">
	      	<property name="contextSource" ref="contextSource" />
		</bean>

	   	<tx:annotation-driven transaction-manager="transactionManager" />
*/

    @Bean
    public ContextSourceTransactionManager contextSourceTransactionManager(ContextSource transactionAwareContextSourceProxy) throws Exception {
        ContextSourceTransactionManager contextSourceTransactionManager = new ContextSourceTransactionManager();
        contextSourceTransactionManager.setContextSource(transactionAwareContextSourceProxy);
        contextSourceTransactionManager.setRenamingStrategy(new DefaultTempEntryRenamingStrategy());
        contextSourceTransactionManager.afterPropertiesSet();
        return contextSourceTransactionManager;
    }


/*
    <bean id="myDataAccessObjectTarget" class="com.example.MyDataAccessObject">
        <property name="ldapTemplate" ref="ldapTemplate" />
    </bean>

    <bean id="myDataAccessObject"  class="org.springframework.transaction.interceptor.TransactionProxyFactoryBean">
        <property name="transactionManager" ref="transactionManager" />
        <property name="target" ref="myDataAccessObjectTarget" />
        <property name="transactionAttributes">
            <props>
                <prop key="*">PROPAGATION_REQUIRES_NEW</prop>
            </props>
        </property>
    </bean>
*/

/*
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.ldapAuthentication()
                .contextSource(contextSource()) //Here is the Problem
                .userDnPatterns(ldapUserDnPatterns)
                .groupSearchBase(ldapGroupSearchBase)
                .groupSearchFilter(ldapGroupSearchFilter)
                .userSearchBase(ldapUserSearchBase);
    }
*/

}
