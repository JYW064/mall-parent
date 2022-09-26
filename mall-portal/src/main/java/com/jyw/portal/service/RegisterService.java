package com.jyw.portal.service;


import com.jyw.portal.domain.User;

public interface RegisterService {
    void insertUser(User user);
    User getUserByName(String username);
}
