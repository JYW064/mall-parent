package com.jyw.portal.service.impl;

import com.jyw.portal.domain.User;
import com.jyw.portal.mapper.UserMapper;
import com.jyw.portal.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    UserMapper userMapper;

    @Override
    public void insertUser(User user) {
       userMapper.insertSelective(user);
    }

    @Override
    public User getUserByName(String username) {
        return userMapper.selectByUsername(username);
    }
}
