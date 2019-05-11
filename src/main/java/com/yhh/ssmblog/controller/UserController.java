package com.yhh.ssmblog.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {
    @RequestMapping("login")
    @ResponseBody
    public String login(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        Subject subject = SecurityUtils.getSubject();
        try {
            if(rememberMe.trim().equalsIgnoreCase("true"))
                token.setRememberMe(true);
            else
                token.setRememberMe(false);
            subject.login(token);
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
        return "true";
    }

    @RequestMapping("checkLogin")
    @ResponseBody
    public JSONObject checkLogin(){
        Subject subject = SecurityUtils.getSubject();
        JSONObject json = new JSONObject();
        if(subject.isAuthenticated()){
            json.put("username",subject.getPrincipal());
            json.put("result","true");
        }else{
            json.put("result","false");
        }
        return json;
    }

    @RequestMapping("logout.do")
    @ResponseBody
    public String logout(){
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return "true";
    }
}
