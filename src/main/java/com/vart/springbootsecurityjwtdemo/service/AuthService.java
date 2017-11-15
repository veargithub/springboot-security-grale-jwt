package com.vart.springbootsecurityjwtdemo.service;

import com.vart.springbootsecurityjwtdemo.config.WebSecurityConfig;
import com.vart.springbootsecurityjwtdemo.dao.UserRepository;
import com.vart.springbootsecurityjwtdemo.domain.User;
import com.vart.springbootsecurityjwtdemo.util.JwtTokenUtil;
import com.vart.springbootsecurityjwtdemo.util.RoleName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;
    private UserRepository userRepository;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private WebSecurityConfig webSecurityConfig;

    @Autowired
    public AuthService(
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtTokenUtil jwtTokenUtil,
            UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
    }

    public User register(User user) {
        if (userRepository.findByUsername(user.username) != null) {
            return null;
        }
        user.password = webSecurityConfig.passwordEncoder().encode(user.password);
        List roles = new ArrayList();
        roles.add(RoleName.USER);
        user.roles = roles;
        return userRepository.save(user);
    }

    public String login(String username, String password) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(userDetails);
        return token;
    }

    public String refresh(String oldToken) {
        return null;
    }
}
