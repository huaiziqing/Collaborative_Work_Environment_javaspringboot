package org.example.book.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.example.book.model.User;

@Mapper
public interface UserRepository {
    // 用户注册
    @Insert("INSERT INTO User(username, password, real_name, email) " +
            "VALUES(#{username}, #{password}, #{realName}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void register(User user);

    // 根据用户名查询用户
    @Select("SELECT * FROM User WHERE username = #{username}")
    User findByUsername(String username);}
