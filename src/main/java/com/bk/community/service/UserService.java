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

    public void createOrUpdateUser(User user) {
        User dbUser = userMapper.findUserByAccountId(user.getAccountId());
        if (dbUser == null) {
            // 新增用户
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        } else {
            // 更新用户
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setBio(user.getBio());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            dbUser.setGmtModified(System.currentTimeMillis());
            userMapper.update(dbUser);
        }
    }
}
