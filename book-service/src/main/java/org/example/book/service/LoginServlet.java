package org.example.book.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.example.book.model.User;
import org.example.book.repository.UserRepository;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/api/login")
public class LoginServlet extends HttpServlet {

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
        User loginUser = objectMapper.readValue(req.getInputStream(), User.class);

        // 查询用户信息
        User user = userRepository.findByUsername(loginUser.getUsername());
        if (user == null || !user.getPassword().equals(loginUser.getPassword())) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write(objectMapper.writeValueAsString(Map.of("error", "用户名或密码错误")));
            return;
        }

        res.setStatus(HttpServletResponse.SC_OK);
        res.getWriter().write(objectMapper.writeValueAsString(Map.of("message", "登录成功", "userId", user.getId())));
    }
}