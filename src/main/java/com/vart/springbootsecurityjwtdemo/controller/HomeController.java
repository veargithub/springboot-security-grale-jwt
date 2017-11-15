package com.vart.springbootsecurityjwtdemo.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @RequestMapping("/home")
    public String home() {
        return "home";
    }

    @PreAuthorize("hasAuthority('USER_ROLE')")
    @RequestMapping("/home/user")
    public String user() {
        return "user";
    }

    @RequestMapping("/home/admin")
    @PreAuthorize("hasAuthority('ADMIN_ROLE')")
    public String admin() {
        return "admin";
    }
}
