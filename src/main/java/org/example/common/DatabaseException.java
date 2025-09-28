package org.example;

/**
 * @author huaiziqing
 */
public class DatabaseException extends GlobalException {

    /**
     * 构造方法
     * @param message 异常信息
     */
    public DatabaseException(String message) {
        super(5001, message);
    }

    /**
     * 构造方法
     * @param message 异常信息
     * @param cause 原始异常
     */
    public DatabaseException(String message, Throwable cause) {
        super(5001, message);
        initCause(cause);
    }
}
