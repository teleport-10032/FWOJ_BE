package com.teleport.fwoj_backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teleport.fwoj_backend.pojo.user;
import com.teleport.fwoj_backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class userController {
    @Autowired
    private userService userServiceObject;
    //验证登陆是否成功
    @RequestMapping(value = "/login",method = {RequestMethod.POST})
    @CrossOrigin
    public String login(@RequestParam("username") String username,
                        @RequestParam("passwd") String passwd) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        HashMap s = new HashMap();
        //status 1 登录成功 0 用户名或密码不正确 -1 被封号
        if(userServiceObject.loginCheck(username,passwd) == 1)
        {
            if(userServiceObject.getAvailableByUsername(username)) {
                s.put("status", "1");
                s.put("token", userServiceObject.createToken(username));
            }
            else
                s.put("status","-1");
        }
        else
            s.put("status","0");
        return mapper.writeValueAsString(s);
    }

    //根据token查询用户名
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
    //根据token查询用户Id
    @RequestMapping(value = "/getUserId",method = {RequestMethod.POST})
    @CrossOrigin
    public String getUserId(@RequestParam("token") String token) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        if(token == null || token.equals(""))
            return null;
        HashMap s = new HashMap();
        s.put("userId",userServiceObject.getUserId(token));
        return mapper.writeValueAsString(s);
    }
    //根据token查询用户Type
    @RequestMapping(value = "/getUserType",method = {RequestMethod.POST})
    @CrossOrigin
    public String getUserType(@RequestParam("token") String token) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        if(token == null || token.equals(""))
            return null;
        HashMap s = new HashMap();
        s.put("userType",userServiceObject.getUserType(token));
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
        boolean usernameE = userServiceObject.usernameIsExist(username);

        //error:1 email exist 2 username exist 3 format error 4sql error
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

    //获取用户列表
    @RequestMapping(value = "/getUserList",method = {RequestMethod.GET})
    @CrossOrigin
    public String getUserList(@RequestParam("page") int page,@RequestParam("pre") int pre,@RequestParam("token") String token,@RequestParam("key") String key) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        HashMap s = new HashMap();
        if(userServiceObject.tokenIsAdmin(token))
        {
            List<user> list = userServiceObject.getUserList(page,pre,key);
            int num = userServiceObject.getUserNum();
            s.put("data",list);
            s.put("num",num);
            s.put("status",1);
        }
        else
            s.put("status",0);
        return  mapper.writeValueAsString(s);
    }
    //根据token查询是否为管理员
    @RequestMapping(value = "/tokenIsAdmin",method = {RequestMethod.GET})
    @CrossOrigin
    public String tokenIsAdmin(@RequestParam("token") String token) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        HashMap s = new HashMap();
        if(userServiceObject.tokenIsAdmin(token))
        {
            s.put("result","1");
        }
        else
            s.put("result","0");
        return mapper.writeValueAsString(s);
    }

    //根据管理员的token和用户id查询用户详细信息
    //根据token查询是否为管理员
    @RequestMapping(value = "/getUserDetailById",method = {RequestMethod.GET})
    @CrossOrigin
    public String getUserDetailById(@RequestParam("token") String token,@RequestParam("id") int id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        HashMap s = new HashMap();
        if(userServiceObject.tokenIsAdmin(token))
        {
            s.put("result","1");
            s.put("userDetail",userServiceObject.getUserDetailById(id));
        }
        else
            s.put("result","0");
        return mapper.writeValueAsString(s);
    }

    //更新用户信息
    //根据管理员的token和用户id查询用户详细信息(带密码)
    @RequestMapping(value = "/updateUser",method = {RequestMethod.POST})
    @CrossOrigin
    public String updateUser(@RequestParam("token") String token, @RequestParam("email") String email, @RequestParam("username") String username,
                             @RequestParam("type") String type, @RequestParam("des") String des, @RequestParam("passwd") String passwd,
                                @RequestParam("id") int id) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        HashMap s = new HashMap();
        int emailNumExcept = userServiceObject.getEmailNumExpect(email,id);
        int usernameNumExcept = userServiceObject.getUsernameNumExpect(username,id);
        //error:1 email exist 2 username exist
        //3 format error 4 sql error
        //5 permission error
        //判断是否是admin权限
        if(userServiceObject.tokenIsAdmin(token))
        {
            if(emailNumExcept != 0)
                s.put("error","1");
            else if(usernameNumExcept  != 0)
                s.put("error","2");
            else if(username.length() > 10 || username.length() < 2 || passwd.length()>35 || email.length() > 30 || (!type.equals("admin") && !type.equals("user")))
                s.put("error","3");
            else
            {
                boolean r;
                r = userServiceObject.editUserDetail(email,username,type,des,passwd,id);
                if(r)
                    s.put("error","0");
                else
                    s.put("error","4");
            }
        }
        else
            s.put("error","5");
        return mapper.writeValueAsString(s);
    }

    //更新用户信息
    //根据管理员的token和用户id查询用户详细信息(带密码)
    @RequestMapping(value = "/updateUserWithoutPasswd",method = {RequestMethod.POST})
    @CrossOrigin
    public String updateUserWithoutPasswd(@RequestParam("token") String token, @RequestParam("email") String email, @RequestParam("username") String username,
                             @RequestParam("type") String type, @RequestParam("des") String des, @RequestParam("id") int id) throws JsonProcessingException {


        ObjectMapper mapper = new ObjectMapper();
        HashMap s = new HashMap();
        int emailNumExcept = userServiceObject.getEmailNumExpect(email,id);
        int usernameNumExcept = userServiceObject.getUsernameNumExpect(username,id);
        //error:1 email exist 2 username exist
        //3 format error 4 sql error
        //5 permission error
        //判断是否是admin权限
        if(userServiceObject.tokenIsAdmin(token))
        {
            if(emailNumExcept != 0)
                s.put("error","1");
            else if(usernameNumExcept  != 0)
                s.put("error","2");
            else if(username.length() > 10 || username.length() < 2  || email.length() > 30 || (!type.equals("admin") && !type.equals("user")))
                s.put("error","3");
            else
            {
                boolean r;
                r = userServiceObject.editUserDetailWithoutPasswd(email,username,type,des,id);
                if(r)
                    s.put("error","0");
                else
                    s.put("error","4");
            }
        }
        else
            s.put("error","5");
        return mapper.writeValueAsString(s);
    }

    //通过id删除用户
    //根据token查询是否为管理员
    @RequestMapping(value = "/deleteUser",method = {RequestMethod.DELETE})
    @CrossOrigin
    public String deleteUser(@RequestParam("token") String token,@RequestParam("id") int id) throws JsonProcessingException {


        ObjectMapper mapper = new ObjectMapper();
        HashMap s = new HashMap();
        if(userServiceObject.tokenIsAdmin(token))
        {
            if(userServiceObject.deleteUser(id))
                s.put("error","0");
        }
        else
            s.put("error","1");
        return mapper.writeValueAsString(s);
    }

    //更改用户的available
    @RequestMapping(value = "/changeUserAvailable",method = {RequestMethod.POST})
    @CrossOrigin
    public String changeUserAvailable (@RequestParam("token") String token,@RequestParam("username") String username) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        HashMap s = new HashMap();
        if(userServiceObject.tokenIsAdmin(token))
        {
            //可用
            if(userServiceObject.getAvailableByUsername(username))
                userServiceObject.setAvailableByUsername(username,false);
            //不可用
            else
                userServiceObject.setAvailableByUsername(username,true);
            s.put("error","0");
        }
        else
            s.put("error","1");

        return mapper.writeValueAsString(s);
    }

    //获取个人信息设置所需数据
    @RequestMapping(value = "/getUserPersonInfo")
    @CrossOrigin
    public String getUserPersonInfo(@RequestParam("token") String token) throws JsonProcessingException {
        System.out.println(token);
        ObjectMapper mapper = new ObjectMapper();
        HashMap s = new HashMap();
        int id = userServiceObject.getUserId(token);
        String username = userServiceObject.getUserName(token);
        String email = userServiceObject.getUserEmail(token);
        String type = userServiceObject.getUserType(token);
        if(id != 0 && username != null && email != null && type != null)
        {
            s.put("error","0");
            s.put("id",id);
            s.put("username",username);
            s.put("email",email);
            s.put("type",type);
        }
        else
            s.put("error","1");

        System.out.println(s);

        return mapper.writeValueAsString(s);
    }

}
