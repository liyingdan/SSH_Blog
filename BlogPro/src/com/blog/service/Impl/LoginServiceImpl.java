package com.blog.service.Impl;

import com.blog.dao.UserDao;
import com.blog.domain.User;
import com.blog.service.LoginService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class LoginServiceImpl implements LoginService {
    private UserDao userDao;
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User login(User user) {
        System.out.println("用户名"+user.getUsername());
        //调用dao 查询用户
       return userDao.getUser(user.getUsername(),user.getPassword());

    }
}
