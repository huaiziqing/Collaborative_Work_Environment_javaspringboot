package org.example.book.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.example.book.model.User;
import org.example.book.repository.UserRepository;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/api/student/user/register")
public class RegisterServlet extends HttpServlet {

    private UserRepository userRepository;

    @Override
    public void init() throws ServletException {
        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) getServletContext().getAttribute("sqlSessionFactory");
        try (SqlSession session = sqlSessionFactory.openSession()) {
            userRepository = session.getMapper(UserRepository.class);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        req.setCharacterEncoding("UTF-8");
        res.setContentType("application/json;charset=UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(req.getInputStream());

        String username = jsonNode.get("username").asText();
        String password = jsonNode.get("password").asText();
        String realName = jsonNode.get("realName").asText();
        String email = jsonNode.get("email").asText();

        // 检查用户名是否已存在
        User existingUser = userRepository.findByUsername(username);
        if (existingUser != null) {
            res.setStatus(HttpServletResponse.SC_CONFLICT);
            res.getWriter().write(objectMapper.writeValueAsString(Map.of("error", "用户名已存在")));
            return;
        }

        // 创建新用户对象并注册
        User user = new User();
        user.setUsername(username);
        user.setPassword(password); // 实际应进行加密处理
        user.setRealName(realName);
        user.setEmail(email);


        res.setStatus(HttpServletResponse.SC_CREATED);
        res.getWriter().write(objectMapper.writeValueAsString(Map.of("message", "注册成功", "userId", user.getId())));
    }
}