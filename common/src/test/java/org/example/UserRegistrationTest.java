package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserRegistrationTest {

    @Test
    public void testHashPassword() {
        UserRegistration reg = new UserRegistration();
        String password = "Abcdefg1";
        String hash1 = reg.hashPassword(password);
        String hash2 = reg.hashPassword(password);
        // 相同输入应产生相同输出
        Assertions.assertEquals(hash1, hash2);
        // 哈希长度应为64（SHA-256十六进制）
        Assertions.assertEquals(64, hash1.length());
    }

    @Test
    public void testRegisterPasswordStrength() {
        UserRegistration reg = new UserRegistration();
        // 合法密码
        String password = "Abcdefg1";
        String hash = reg.register("user1", password);
        Assertions.assertNotNull(hash);
        // 不合法密码：无大写
        Assertions.assertThrows(IllegalArgumentException.class, () -> reg.register("user2", "111111"));
        // 不合法密码：无小写
        Assertions.assertThrows(IllegalArgumentException.class, () -> reg.register("user3", "2222"));
        // 不合法密码：无数字
        Assertions.assertThrows(IllegalArgumentException.class, () -> reg.register("user4", "Abcdefgh"));
        // 不合法密码：太短
        Assertions.assertThrows(IllegalArgumentException.class, () -> reg.register("user5", "Ab1"));
    }
} 