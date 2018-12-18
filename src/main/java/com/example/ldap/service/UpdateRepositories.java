package com.example.ldap.service;

import org.springframework.context.ApplicationContext;
import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * https://stackoverflow.com/questions/45319900/multiple-ldap-repositories-with-spring-ldap-repository
 * https://stackoverflow.com/questions/45319900/multiple-ldap-repositories-with-spring-ldap-repository
 */
@Service
public class UpdateRepositories {

/*
    public void updateAllRepositories(LdapUserRepository userRepository1, LdapUserRepository userRepository2) {
        // apply updates to userRepository1 and userRepository2
    }
*/

    public void updateAllRepositories(ApplicationContext appContext) {
//        Map<String, LdapRepository> ldapRepositories = appContext.getBeansofType(LdapRepository.class);
        List<LdapRepository> ldapUserRepoList = new ArrayList<>(appContext.getBeansOfType(LdapRepository.class).values());
        // iterate through map and apply updates
    }

}
