package com.vart.springbootsecurityjwtdemo.service;

import com.vart.springbootsecurityjwtdemo.dao.UserRepository;
import com.vart.springbootsecurityjwtdemo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("该用户不存在");
        }
        System.out.println("username:" + user.username + ", password:" + user.password + ", role:"
        + user.roles);
        return user;
    }
}
