package com.vart.springbootsecurityjwtdemo.service;

import com.vart.springbootsecurityjwtdemo.config.WebSecurityConfig;
import com.vart.springbootsecurityjwtdemo.dao.RoleRepository;
import com.vart.springbootsecurityjwtdemo.dao.UserRepository;
import com.vart.springbootsecurityjwtdemo.domain.Role;
import com.vart.springbootsecurityjwtdemo.domain.User;
import com.vart.springbootsecurityjwtdemo.util.RoleName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class InitService {

    final String ADMIN1 = "admin1";
    final String USER1 = "user1";

    @Autowired
    private WebSecurityConfig webSecurityConfig;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        System.out.println("init...");

        Role roleAdmin = createRole(RoleName.ADMIN);
        Role roleUser = createRole(RoleName.USER);

        User admin = createUser(ADMIN1, roleAdmin);
        User user = createUser(USER1, roleUser);
        User user2 = createUser("user2", roleUser);

//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String hashedPassword = passwordEncoder.encode("123456");
//
//        boolean matches = passwordEncoder.matches("123456", hashedPassword);
//        System.out.println("match:" + matches);


    }

    private Role createRole(String rolename) {
        Role role = roleRepository.findByRolename(rolename);
        if (role != null) {
            System.out.println("find role:" + rolename);
            return role;
        }
        System.out.println(rolename + " creating");
        role = new Role();
        role.rolename = rolename;
        return roleRepository.saveAndFlush(role);
    }

    private User createUser(String username, Role role) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            System.out.println(username + " creating...");
            System.out.println("role id:" + role.id);
            //admin1.id = 0;
            user = new User();
            user.username = username;
            String password = webSecurityConfig.passwordEncoder().encode("123456");
            System.out.println("password:" + password);
            System.out.println("password length:" + password.length());
            user.password = password;
            List roles = new ArrayList();
            roles.add(role);
            user.roles = roles;
            return userRepository.saveAndFlush(user);
        } else {
            System.out.println(username + " created");
            return user;
        }
    }

}
