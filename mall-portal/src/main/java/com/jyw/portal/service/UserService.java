package com.jyw.portal.service;

import com.jyw.portal.domain.User;

public interface UserService {
    User getUserInfo(long userId);

    void updateUser(User user);
}
