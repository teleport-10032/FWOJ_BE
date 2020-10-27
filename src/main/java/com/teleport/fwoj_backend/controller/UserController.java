package com.teleport.fwoj_backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teleport.fwoj_backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class UserController {
    @Autowired
    private userService userServiceObject;
    //验证登陆是否成功
    @RequestMapping(value = "/login",method = {RequestMethod.POST})
    @CrossOrigin
    public String login(@RequestParam("username") String username,
                        @RequestParam("passwd") String passwd) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        HashMap s = new HashMap();
        if(userServiceObject.loginCheck(username,passwd) == 1)
        {
            s.put("status","1");
            s.put("token",userServiceObject.createToken(username));
        }
        else
            s.put("status","0");
        return mapper.writeValueAsString(s);
    }

    //根据token查询用户名 用户类型
    @RequestMapping(value = "/getUserName",method = {RequestMethod.POST})
    @CrossOrigin
    public String getUserName(@RequestParam("token") String token) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        if(token == null || token.equals(""))
            return null;
        HashMap s = new HashMap();
        s.put("username",userServiceObject.getUserName(token));
        return mapper.writeValueAsString(s);
    }

    //传入email username passwd 注册
    @RequestMapping(value = "/register",method = {RequestMethod.POST})
    @CrossOrigin
    public String register(@RequestParam("email") String email,@RequestParam("username") String username,@RequestParam("passwd") String passwd)
            throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        HashMap s = new HashMap();

        boolean emailE = userServiceObject.emailExist(email);
        boolean usernameE = userServiceObject.usernameExist(username);

        //error:1 email exist 2 username exist 3 format error sql error
        if(emailE)
            s.put("error","1");
        else if(usernameE)
            s.put("error","2");
        else if(username.length() > 10 || username.length() < 2 || passwd.length()>35 || email.length() > 30)
            s.put("error","3");
        else
        {
            boolean r = userServiceObject.register(email,username,passwd);
            if(r)
                s.put("error","0");
            else
                s.put("error","4");
        }
        return mapper.writeValueAsString(s);
    }
}
