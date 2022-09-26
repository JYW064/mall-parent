package com.jyw.portal.service.impl;

import com.jyw.portal.domain.User;
import com.jyw.portal.mapper.UserMapper;
import com.jyw.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: mall-parent
 * @description:
 * @author: ji yi wei
 * @create: 2022-04-18
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User getUserInfo(long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }
}
