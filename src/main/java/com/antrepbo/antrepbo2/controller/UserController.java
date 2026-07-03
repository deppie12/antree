package com.antrepbo.antrepbo2.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.antrepbo.antrepbo2.model.User;
import com.antrepbo.antrepbo2.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String registerUser(
            @RequestParam String username,
            @RequestParam String password
    ) {

        User user = new User();

        user.setUsername(username);
        user.setPassword(password);

        userService.saveUser(user);

        return "redirect:/login.html";
    }
}
