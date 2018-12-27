package com.example.ldap.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * https://stackoverflow.com/questions/45319900/multiple-ldap-repositories-with-spring-ldap-repository
 *
 */
@Slf4j
@Service
public class UpdateRepositories {

    @Autowired
    private ApplicationContext appContext;


/*
    public void updateAllRepositories(LdapUserRepository userRepository1, LdapUserRepository userRepository2) {
        // apply updates to userRepository1 and userRepository2
    }
*/

    public void updateAllRepositories() {//ApplicationContext appContext) {
//        Map<String, LdapRepository> ldapRepositories = appContext.getBeansofType(LdapRepository.class);
        List<LdapContextSource> ldapUserRepoList = new ArrayList<>(appContext.getBeansOfType(LdapContextSource.class).values());
        // iterate through map and apply updates
        for(LdapContextSource source : ldapUserRepoList) {
            log.info("userDn: {}", source.getUserDn());

        }
    }

}
