package com.chainlink.login_register.controller;

import com.chainlink.login_register.domain.User;
import com.chainlink.login_register.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public boolean register(@RequestBody User user){
        return userService.register(user);
    }

    @GetMapping
    public boolean logIn(@RequestBody User user){
       if(userService.logIn(user)==null){
           return false;
       }
        return true;
    }

}
