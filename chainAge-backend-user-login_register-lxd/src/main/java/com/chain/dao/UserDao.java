package com.chain.dao;

import com.chain.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface UserDao {
        @Insert("insert into user(username,password) values (#{username},#{password})")
        public void register(User user);

        @Select("select *from user where username=#{username} and password=#{password}")
        public User logIn(User user);

        @Select("select id from user where username=#{username} and password=#{password}")
        public Integer selectAll(User user);
}
