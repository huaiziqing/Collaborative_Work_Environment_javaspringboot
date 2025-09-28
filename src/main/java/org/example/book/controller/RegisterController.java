package org.example.book.controller;

import org.example.book.model.User;
import org.example.book.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // 检查用户名是否已存在
            User existingUser = userRepository.findByUsername(user.getUsername());
            if (existingUser != null) {
                response.put("error", "用户名已存在");
                return response;
            }
            
            // 注册新用户
            userRepository.register(user);
            response.put("message", "注册成功");
            response.put("userId", user.getId());
        } catch (Exception e) {
            response.put("error", "注册失败: " + e.getMessage());
        }
        
        return response;
    }
}