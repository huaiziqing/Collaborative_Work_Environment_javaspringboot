package org.example.book.controller;

import org.example.book.model.User;
import org.example.book.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User loginUser) {
        // 查询用户信息
        User user = userRepository.findByUsername(loginUser.getUsername());
        Map<String, Object> response = new HashMap<>();
        
        if (user == null || !user.getPassword().equals(loginUser.getPassword())) {
            response.put("error", "用户名或密码错误");
            return response;
        }

        response.put("message", "登录成功");
        response.put("userId", user.getId());
        return response;
    }
}