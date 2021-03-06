package com.example.ldap.controller;

import java.util.List;

import com.example.ldap.domain.User;
import com.example.ldap.service.LdapUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.NameAlreadyBoundException;
import org.springframework.ldap.NameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * https://github.com/wmfairuz/spring-ldap-user-admin/blob/master/src/main/java/sample/controller/LdapUserController.java
 */
@Slf4j
@RestController
public class LdapUserController {

//    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public LdapUserService ldapUserService;

//    @Autowired
//    public LdapGroupService ldapGroupService;

    @RequestMapping("/test/usernames")
    @ResponseBody
    public List<String> getUserNameList() {
        log.info("Testing person name list");
        return ldapUserService.getAllPersonNames();
    }

    @RequestMapping("/test/users")
    @ResponseBody
    public List<User> getUserList() {
        log.info("Testing person object list");
        return ldapUserService.getAllPersons();
    }

    /**
     * http://localhost:9998/test/userlookup?uid=prueba
     * http://localhost:8080/spring-ldap-rest/test/userlookup?uid=prueba
     * @param uid
     * @return
     */
    @RequestMapping("/test/userlookup")
    @ResponseBody
    public User getUserByString(@RequestParam(value="uid", defaultValue="test.001") String uid) {
        log.info("Testing user lookup using String Dn");
//        String str = "uid=" + uid + ",ou=people";
        String str = "cn=" + uid + ",ou=bbvaespana";
        return ldapUserService.findUserByString(str);
    }

    @RequestMapping("/test/userlookup2")
    @ResponseBody
    public User getUser(@RequestParam(value="uid", defaultValue="test.001") String uid) {
        log.info("Testing user lookup");
        User user = new User();
        user.setUid(uid);
        return ldapUserService.findUser(user);
    }

    @RequestMapping("/test/userbind")
    @ResponseBody
    public User createUser(@RequestParam(value="uid", defaultValue="test.001") String uid) {
        log.info("Testing user binding");

        User user = new User(uid, "test", uid, "password");
        try {
            ldapUserService.createUser(user);
        } catch (NameAlreadyBoundException e) {
            log.error("User already exist!!!");
        } catch (Exception e) {
            log.error("Something unknown happened >>> " + e.getClass().getName());
            log.error("ERROR", e);
        }
        return user;
    }

    @RequestMapping("/test/userunbind")
    @ResponseBody
    public User deleteUser(@RequestParam(value="uid", defaultValue="test.001") String uid) {
        log.info("Testing user binding");

        User user = new User(uid);
        try {
            ldapUserService.delete(user);
        } catch (NameNotFoundException e) {
            log.error("User not found!!!");
        } catch (Exception e) {
            log.error("Something unknown happened >>> " + e.getClass().getName());
            log.error("ERROR", e);
        }
        return user;
    }

    @RequestMapping("/test/userupdate")
    @ResponseBody
    public User updateUser(@RequestParam(value="lastname", defaultValue="test.001") String lastname,
                           @RequestParam(value="uid", defaultValue="test.001") String uid) {
        log.info("Testing user update");

        User user = null;
        try {
            User _user = new User(uid);
            user = ldapUserService.findUser(_user);
            user.setLastName(lastname);

            ldapUserService.update(user);
        } catch (NameNotFoundException e) {
            log.error("User not found!!!");
        } catch (Exception e) {
            log.error("Something unknown happened >>> " + e.getClass().getName());
            log.error("ERROR", e);
        }
        return user;
    }

/*
    @RequestMapping("/test/addgroup")
    @ResponseBody
    public User addMemberToGroup(@RequestParam(value="group") String group,
                                 @RequestParam(value="uid", defaultValue="test.001") String uid) {
        log.info("Testing user add to group");

        User user = null;
        try {
            User _user = new User(uid);
            user = ldapUserService.findUser(_user);
        } catch (NameNotFoundException e) {
            log.error("User not found!!!");
            log.error("ERROR", e);
        } catch (Exception e) {
            log.error("Something unknown happened >>> " + e.getClass().getName());
            log.error("ERROR", e);
        }

        try {
            ldapGroupService.addMemberToGroup(group, user);
        } catch (NameNotFoundException e) {
            log.error("Group not found!!!");
            log.error("ERROR", e);
        } catch (Exception e) {
            log.error("Something unknown happened >>> " + e.getClass().getName());
            log.error("ERROR", e);
        }
        return user;
    }
*/

/*
    @RequestMapping("/test/deletegroup")
    @ResponseBody
    public User removeMemberFromGroup(@RequestParam(value="group") String group,
                                      @RequestParam(value="uid", defaultValue="test.001") String uid) {
        log.info("Testing user add to group");

        User user = null;
        try {
            User _user = new User(uid);
            user = ldapUserService.findUser(_user);
        } catch (NameNotFoundException e) {
            log.error("User not found!!!");
            log.error("ERROR", e);
        } catch (Exception e) {
            log.error("Something unknown happened >>> " + e.getClass().getName());
            log.error("ERROR", e);
        }

        try {
            ldapGroupService.removeMemberFromGroup(group, user);
        } catch (NameNotFoundException e) {
            log.error("Group not found!!!");
            log.error("ERROR", e);
        } catch (Exception e) {
            log.error("Something unknown happened >>> " + e.getClass().getName());
            log.error("ERROR", e);
        }
        return user;
    }
*/

}
