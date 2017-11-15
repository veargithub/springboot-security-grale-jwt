package com.vart.springbootsecurityjwtdemo.controller;

import com.vart.springbootsecurityjwtdemo.Msg;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @RequestMapping("/")
    public String index(Model model) {
        Msg msg = new Msg("测试标题","测试内容", "额外信息，只对管理员显示");
        model.addAttribute("msg", msg);
        return "index";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/test1")
    @ResponseBody
    public String adminTest1() {
        return "ROLE_ADMIN";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping("/admin/test2")
    @ResponseBody
    public String adminTest2() {
        return "ROLE_USER";
    }
}
