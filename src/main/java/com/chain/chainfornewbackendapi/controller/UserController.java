package com.chain.chainfornewbackendapi.controller;

import com.chain.chainfornewbackendapi.domain.User;
import com.chain.chainfornewbackendapi.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
//@CrossOrigin(origins = "*")
@Api(tags = "用户登录与注册")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    @ApiOperation("用户注册")
    public String register(@RequestBody User user){
        if(userService.register(user)==true){
            System.out.println( user.getUsername()+"  "+"register succeeded");
            return "注册成功";
        }
        else{
            System.out.println(user.getUsername()+"  "+"register failed,username has already existed");
            return "注册失败,用户名已存在";
        }
    }

    @PostMapping("/login")
    @ApiOperation("用户登录")
    public String logIn(@RequestBody User user){
       if(userService.logIn(user)==null){
           System.out.println(user.getUsername()+"  "+"login failed,user doesn't exist or password doesn't correct");
           return "用户不存在或密码错误";
       }
        System.out.println(user.getUsername()+"  "+"login succeeded");
        return "登录成功";
    }
}
