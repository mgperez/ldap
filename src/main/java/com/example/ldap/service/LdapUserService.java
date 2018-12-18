package com.example.ldap.service;

import com.example.ldap.domain.LdapUserDaoImpl;

import com.example.ldap.domain.User;
import org.springframework.ldap.NameAlreadyBoundException;
import org.springframework.ldap.NameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * https://github.com/wmfairuz/spring-ldap-user-admin/blob/master/src/main/java/sample/service/LdapUserService.java
 */
@Component
public class LdapUserService {

    @Autowired
    public LdapUserDaoImpl ldapUserDao;

    public List<String> getAllPersonNames() {
        return ldapUserDao.getAllPersonNames();
    }

    public List<User> getAllPersons() {
        return ldapUserDao.getAllPersons();
    }

    public User findUserByString(String dn) throws NameNotFoundException {
        return ldapUserDao.findUserByString(dn);
    }

    public User findUser(User user) throws NameNotFoundException {
        return ldapUserDao.findUser(user);
    }

    public void createUser(User user) throws NameAlreadyBoundException {
        ldapUserDao.create(user);
    }

    public void delete(User user) throws NameNotFoundException {
        ldapUserDao.delete(user);
    }

    public void update(User user) throws NameNotFoundException {
        ldapUserDao.update(user);
    }
}