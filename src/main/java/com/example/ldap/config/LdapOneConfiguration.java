package com.example.ldap.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.ldap.LdapProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
import org.springframework.transaction.interceptor.TransactionProxyFactoryBean;

import java.util.Collections;
import java.util.Properties;


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
 *
 * https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-ldap
 *
 */
@Configuration
//@EnableConfigurationProperties(LdapProperties.class)
@EnableTransactionManagement(proxyTargetClass = true)
public class LdapOneConfiguration {

    public static final String CONNECT_TIMEOUT_ENV = "com.sun.jndi.ldap.connect.timeout";
    public static final String READ_TIMEOUT_ENV = "com.sun.jndi.ldap.read.timeout";

    @Autowired
    private Environment environment;


//    @Bean(name="ldapPropertiesOne")
//    @ConfigurationProperties(prefix="one.ldap.contextsource")
//    public LdapProperties ldapPropertiesOne() {
//        return new LdapProperties();
//    }


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

    @Bean(name = "poolingContextSourceOne")
    public ContextSource poolingLdapOneContextSource(@Qualifier("contextSourceOneTarget")LdapContextSource contextSourceLdapOneTarget) {

        PoolingContextSource poolingContextSource = new PoolingContextSource();
//        poolingContextSource.setDirContextValidator(new DefaultDirContextValidator());
        poolingContextSource.setDirContextValidator(dirContextValidator());
        poolingContextSource.setContextSource(contextSourceLdapOneTarget);
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

    @Bean(name = "contextSourceOneTarget")
    @ConfigurationProperties(prefix="one.ldap.contextsource")
    public LdapContextSource  contextSourceLdapOneTarget(){//@Qualifier("ldapPropertiesOne") LdapProperties ldapProperties) {
        final LdapContextSource contextSource = new LdapContextSource();
//        contextSource.setAnonymousReadOnly(true);
//        contextSource.setUrl("ldap://" + ldapHost + ":" + ldapPort);
//        s.setUrl(String.format("ldap://localhost:%d", port));
//        contextSource.setUrls(ldapUrls.toArray(new String[]{}));
//        contextSource.setUrl("ldap://localhost:3060"); // LDAP Url
//        contextSource.setUrls(ldapProperties.getUrls());

//        <property name="base" value="${ldapConfig.base}" />
//        contextSource.setBase("dc=mycompany,dc=com"); //Base directory
//        contextSource.setBase(ldapProperties.getBase());


//        contextSource.setUserDn("cn=ldapAdmin"); // User to connect to LDAP //ldapUsername);
//        contextSource.setUserDn(this.properties.getUsername());
//        contextSource.setUserDn(ldapProperties.getUsername());
//        contextSource.setPassword("password1"); // User password //ldapPassword);
//        contextSource.setPassword(ldapProperties.getPassword());

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

//        source.setBaseEnvironmentProperties(Collections.<String,Object>unmodifiableMap(this.properties.getBaseEnvironment()));
//        contextSource.setBaseEnvironmentProperties(
//                Collections.<String, Object>unmodifiableMap(ldapProperties.getBaseEnvironment()));

        return contextSource;
    }


    @Bean(name = "contextSourceOne")
    public ContextSource transactionAwareContextSourceProxy(@Qualifier("poolingContextSourceOne")ContextSource poolingLdapContextSource) {
        return new TransactionAwareContextSourceProxy(poolingLdapContextSource);
    }


/*
    <bean id="ldapTemplate" class="org.springframework.ldap.core.LdapTemplate">
	      	<constructor-arg ref="contextSource" />
	   	</bean>
*/

    @Bean(name = "ldapTemplateOne")
    public LdapTemplate ldapOneTemplate(@Qualifier("contextSourceOne")ContextSource contextSource) {
        LdapTemplate ldapTemplate = new LdapTemplate(contextSource);
//        ldapTemplate.setIgnorePartialResultException(true);
        return ldapTemplate;
    }

/*
    @Bean(name = "ldapUserRepoOne")
    public LdapUserRepository ldapUserRepositoryOne(@Qualifier("ldapTemplateOne") LdapTemplate ldapTemplate,
                                                    @Qualifier("odm") ObjectDirectoryMapper odm) {
        return new LdapUserRepository(ldapTemplate, odm);
    }

    @Bean(name = "ldapFamilyRepoOne")
    public LdapFamilyRepository ldapFamilyRepositoryOne(@Qualifier("ldapTemplateOne") LdapTemplate ldapTemplate,
                                                        @Qualifier("odm") ObjectDirectoryMapper odm) {
        return new LdapFamilyRepository(ldapTemplate, odm);
    }
*/

/*
    <bean id="transactionManager" class="org.springframework.ldap.transaction.compensating.manager.ContextSourceTransactionManager">
	      	<property name="contextSource" ref="contextSource" />
		</bean>

	   	<tx:annotation-driven transaction-manager="transactionManager" />
*/

    @Bean(name="transactionManagerOne")
    public ContextSourceTransactionManager contextSourceOneTransactionManager(@Qualifier("contextSourceOne")ContextSource transactionAwareContextSourceProxy) throws Exception {
        ContextSourceTransactionManager contextSourceTransactionManager = new ContextSourceTransactionManager();
        contextSourceTransactionManager.setContextSource(transactionAwareContextSourceProxy);

        DefaultTempEntryRenamingStrategy renamingStrategy = new DefaultTempEntryRenamingStrategy();
        renamingStrategy.setTempSuffix(DefaultTempEntryRenamingStrategy.DEFAULT_TEMP_SUFFIX);
        contextSourceTransactionManager.setRenamingStrategy(renamingStrategy);


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
    @Bean(name="transactionProxyFactoryOne")
    public TransactionProxyFactoryBean transactionProxyFactoryOne(@Qualifier("transactionManagerOne")ContextSourceTransactionManager transactionManagerOne) {
        TransactionProxyFactoryBean transactionProxyFactoryBean = new TransactionProxyFactoryBean();
        transactionProxyFactoryBean.setTransactionManager(transactionManagerOne);
        transactionProxyFactoryBean.setTarget(ldapDaoTarget);

        Properties transactionAttributes = new Properties();
        transactionAttributes.setProperty("*", "PROPAGATION_REQUIRES_NEW");
        transactionProxyFactoryBean.setTransactionAttributes(transactionAttributes);

    };
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
