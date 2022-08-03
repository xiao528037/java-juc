package com.xiao.juc.review._06_aqs.CyclicBarrier;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-08-03 14:52:58
 * @description
 */
public class CustomizeThreadFactory implements ThreadFactory {
    private String threadPoolName;
    private AtomicInteger count;

    public CustomizeThreadFactory(String threadPoolName) {
        this.threadPoolName = threadPoolName;
        this.count = new AtomicInteger(0);
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, threadPoolName + "-" + count.incrementAndGet());
    }
}
