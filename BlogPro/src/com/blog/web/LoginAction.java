package com.blog.web;

import com.blog.domain.User;
import com.blog.service.LoginService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import javax.swing.*;

/*实现modelDriven是为了接收参数，并且封装成什么类型*/
public class LoginAction extends ActionSupport implements ModelDriven<User> {
    private User user = new User();

    @Override
    public User getModel() {
        return user;
    }

    //注入业务层
    private LoginService loginService;
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    /*登录*/
    public String login(){
        //登录业务逻辑
        User resUser = loginService.login(user);
        if(resUser == null){
            //错误信息回显
            this.addActionError("用户名或密码错误");
            //结果页显示
            return LOGIN;
        }else{
            //保存用户信息
            ActionContext.getContext().getSession().put("curUser",resUser);
            return SUCCESS;
        }

    }

    /*退出*/
    public String loginOut(){
        //清除session
        ActionContext.getContext().getSession().remove("curUser");
        System.out.println("刚刚有人退出系统了");
        return "login_out";
    }


}
