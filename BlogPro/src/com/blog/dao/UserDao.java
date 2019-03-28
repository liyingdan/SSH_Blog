package com.blog.dao;

import com.blog.domain.User;

public interface UserDao {
    public User getUser(String username,String password);
}
