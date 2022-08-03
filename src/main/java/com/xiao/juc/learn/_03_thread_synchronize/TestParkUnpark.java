package com.xiao.juc.learn._03_thread_synchronize;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * projectName: java-juc
 * <p>
 * author: aloneMan
 * <p>
 * createTime: 2022-07-11 18:49:12
 * <p>
 * description：Park and Unpark
 */
@Slf4j
public class TestParkUnpark {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread((
        ) -> {
            log.debug("{} 开始1....", Thread.currentThread().getName());
            LockSupport.park();
            log.debug("{} 结束2....", Thread.currentThread().getName());
            log.debug("{} 开始2....", Thread.currentThread().getName());
            LockSupport.park();
            log.debug("{} 结束2....", Thread.currentThread().getName());
        }, "t1");
        t1.start();
        TimeUnit.SECONDS.sleep(1);
        t1.interrupt();
        log.debug("解锁完成");
        LockSupport.unpark(t1);
        LockSupport.unpark(t1);
    }
}
