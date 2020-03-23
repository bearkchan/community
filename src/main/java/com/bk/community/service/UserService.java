package com.bk.community.service;

import com.bk.community.mapper.UserMapper;
import com.bk.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void insertUser(User user) {
        userMapper.insert(user);
    }

    public User findUserByToken(String token) {
        return userMapper.findUserByToken(token);
    }
}
