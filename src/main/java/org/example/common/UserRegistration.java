package org.example;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public class UserRegistration {
    // 密码强度正则：必须包含大写、小写字母和数字，长度8-32
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,32}$");

    /**
     * 注册用户，校验密码强度并加密
     * @param username 用户名
     * @param password 明文密码
     * @return 加密后的密码
     * @throws IllegalArgumentException 密码不符合要求
     */
    public String register(String username, String password) {
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            throw new IllegalArgumentException("密码必须包含大写字母、小写字母和数字，且长度为8-32位");
        }
        return hashPassword(password);
    }

    /**
     * 使用SHA-256对密码进行哈希加密
     * @param password 明文密码
     * @return 哈希后的密码（十六进制字符串）
     */
    public String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256算法不可用", e);
        }
    }
} 