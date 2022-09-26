package com.jyw.portal.service;


import com.jyw.portal.domain.User;

public interface LoginService {
    User getUserByUsername(String username);

}
