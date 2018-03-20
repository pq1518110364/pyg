package com.pinyougou.manager.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
/**
 * 用户名的显示
 * */
@RestController
@RequestMapping("/login")
public class LoginController {
    @RequestMapping("/name")
    public Map login(){
        //securityContextHolder   session管理器,如果登录过,用户信息会入其中
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        //获得用户的用户名
        Map map = new HashMap<>();
        map.put("loginName",name);
        return map;
    }
}
