package com.example.ldap.service;

import com.example.ldap.domain.LdapUserDaoImpl;

import com.example.ldap.domain.User;
import org.springframework.ldap.NameAlreadyBoundException;
import org.springframework.ldap.NameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * https://github.com/wmfairuz/spring-ldap-user-admin/blob/master/src/main/java/sample/service/LdapUserService.java
 * https://github.com/spring-projects/spring-ldap/blob/master/samples/user-admin/src/main/java/org/springframework/ldap/samples/useradmin/service/UserService.java
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

    @Transactional(value = "transactionManagerOne", readOnly = true)
    public User findUserByString(String dn) throws NameNotFoundException {
        return ldapUserDao.findUserByString(dn);
    }

    public User findUser(User user) throws NameNotFoundException {
        return ldapUserDao.findUser(user);
    }

    @Transactional(value = "transactionManagerOne")
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