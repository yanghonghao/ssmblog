package com.yhh.ssmblog.service;

import com.yhh.ssmblog.entity.User;

import java.util.List;

public interface UserService {
    public boolean insertUser(String username,String password);
    public boolean insertUser(String username,String password,String name);
    public List<User> listUser();
}
