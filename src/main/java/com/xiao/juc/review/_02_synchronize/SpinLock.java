package com.xiao.juc.review._02_synchronize;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-23 16:12:39
 * @description
 */
@Slf4j(topic = "c.spin")
public class SpinLock {
    private AtomicReference<Thread> atomicReference = new AtomicReference<>();
    private Thread currentThread = Thread.currentThread();

    public void lock() {

        while (!atomicReference.compareAndSet(null, currentThread)) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("{}  线程尝试获取锁失败...", Thread.currentThread().getName());
        }
        log.debug("{}  获取锁成功.", Thread.currentThread());

    }

    public void unLock() {
        if (atomicReference.compareAndSet(currentThread, null))
            log.debug("{} 释放锁成功...", currentThread);
    }

}
