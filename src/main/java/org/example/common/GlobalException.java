package org.example;


/**
 * @author huaiziqing
 */
public class GlobalException extends RuntimeException {

    /**
     * 全局自定义异常类，用于统一处理应用程序中的异常情况
     * 异常错误码
     */
    private final int errorCode;

    /**
     * 构造方法
     * @param message 异常信息
     */
    public GlobalException(String message) {
        this(500, message);
    }

    /**
     * 构造方法
     * @param errorCode 错误代码
     * @param message 异常信息
     */
    public GlobalException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * 获取错误代码
     * @return 错误代码
     */
    public int getErrorCode() {
        return errorCode;
    }
}
