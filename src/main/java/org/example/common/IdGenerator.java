package org.example;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author huaiziqing
 */
public class IdGenerator {

    /**
     * 默认起始ID
     */
    private static final long DEFAULT_START_ID = 1000;

    /**
     * 原子计数器
     */
    private final AtomicLong counter;

    /**
     * 构造方法
     * @param startId 起始ID
     */
    public IdGenerator(long startId) {
        counter = new AtomicLong(startId);
    }

    /**
     * 默认构造方法，使用默认起始ID
     */
    public IdGenerator() {
        this(DEFAULT_START_ID);
    }

    /**
     * 生成下一个ID
     * @return 下一个唯一ID
     */
    public long generateId() {
        return counter.incrementAndGet();
    }

    /**
     * 获取当前计数器值
     * @return 当前计数器值
     */
    public long getCurrentValue() {
        return counter.get();
    }
}
