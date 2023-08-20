package com.chainlink.login_register.dao;

import com.chainlink.login_register.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
@Component
@Mapper
public interface UserDao {
        @Insert("insert into user(username,password) values (#{username},#{password})")
        public void register(User user);

        @Select("select *from user where username=#{username} and password=#{password}")
        public User logIn(User user);

        @Select("select id from user where username=#{username}")
        public Integer selectAll(User user);
}
