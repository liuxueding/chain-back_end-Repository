package com.chain.chainfornewbackendapi.service;

import com.chain.chainfornewbackendapi.domain.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService {
    public boolean register(User user);
    public User logIn(User user);
    public boolean selectAll(User user);
}
