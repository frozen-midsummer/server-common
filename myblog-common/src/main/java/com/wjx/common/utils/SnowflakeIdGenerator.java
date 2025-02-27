package com.wjx.common.utils;

import java.util.concurrent.atomic.AtomicLong;

public class SnowflakeIdGenerator {

    // 自定义纪元（起始时间戳），这里设置为2025年1月1日
    private final static long CUSTOM_EPOCH = 1672531200000L; // 2023-01-01

    // 工作机器ID所占的位数
    private final static long WORKER_ID_BITS = 5L;
    // 数据中心ID所占的位数
    private final static long DATA_CENTER_ID_BITS = 5L;
    // 支持的最大工作机器ID，结果是31 (这个移位算法可以简单计算出范围)
    private final static long MAX_WORKER_ID = -1L ^ (-1L << WORKER_ID_BITS);
    // 支持的最大数据中心ID，结果是31
    private final static long MAX_DATA_CENTER_ID = -1L ^ (-1L << DATA_CENTER_ID_BITS);
    // 序列在ID中占的位数
    private final static long SEQUENCE_BITS = 12L;

    private static SnowflakeIdGenerator INSTANCE;

    private final static long WORKER_ID_SHIFT = SEQUENCE_BITS;
    private final static long DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    private final static long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS;
    private final static long SEQUENCE_MASK = -1L ^ (-1L << SEQUENCE_BITS);

    private long workerId;
    private long dataCenterId;
    private long sequence = 0L;

    private AtomicLong lastTimestamp = new AtomicLong(-1L);

    public SnowflakeIdGenerator(long workerId, long dataCenterId) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", MAX_WORKER_ID));
        }
        if (dataCenterId > MAX_DATA_CENTER_ID || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("data center Id can't be greater than %d or less than 0", MAX_DATA_CENTER_ID));
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    public synchronized long nextId() {
        long timestamp = timeGen();

        if (timestamp < lastTimestamp.get()) {
            throw new RuntimeException(String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", lastTimestamp.get() - timestamp));
        }

        if (lastTimestamp.get() == timestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp.get());
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp.set(timestamp);

        return ((timestamp - CUSTOM_EPOCH) << TIMESTAMP_LEFT_SHIFT) |
                (dataCenterId << DATA_CENTER_ID_SHIFT) |
                (workerId << WORKER_ID_SHIFT) |
                sequence;
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }

    private synchronized static SnowflakeIdGenerator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SnowflakeIdGenerator(1, 1);
        }
        return INSTANCE;
    }

    public static long newSnowflakeId() {
        return SnowflakeIdGenerator.getInstance().nextId();
    }
}
