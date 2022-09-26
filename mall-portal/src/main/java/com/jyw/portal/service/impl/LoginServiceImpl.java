package com.jyw.portal.service.impl;

import com.jyw.portal.domain.User;
import com.jyw.portal.mapper.UserMapper;
import com.jyw.portal.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    UserMapper userMapper;
    @Override
    public User getUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }
}
