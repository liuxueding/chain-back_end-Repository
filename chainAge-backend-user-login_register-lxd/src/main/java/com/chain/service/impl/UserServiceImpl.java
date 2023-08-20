package com.chain.service.impl;

import com.chain.dao.UserDao;
import com.chain.domain.User;
import com.chain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public boolean register(User user) {
        boolean ret = selectAll(user);
        if(ret==false){
            userDao.register(user);
            return true;
        }
        else
            return false;
    }

    @Override
    public User logIn(User user) {
        User ret = userDao.logIn(user);
        return ret;
    }

    @Override
    public boolean selectAll(User user) {
        Integer id= userDao.selectAll(user);
        if (id==null){
            return false;
        }
        return true;
    }
}
