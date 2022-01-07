package com.mydata.crm.settings.service;

import com.mydata.crm.settings.domain.User;
import com.mydata.crm.exception.LoginException;

import java.util.List;

public interface UserService {
    User login(String loginAct, String loginPwd, String ip) throws LoginException;

    List<User> getUserList();
}
