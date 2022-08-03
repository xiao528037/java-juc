package com.xiao.juc.learn._02_mian_guard_thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * projectName: java-juc
 * package:com.xiao.juc._02_mian_guard_thread
 * author: aloneMan
 * createTime: 2022-07-09 11:45:16
 * description：
 */
@Slf4j
public class Thread_daemon {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug(Thread.currentThread().getName()+"开始执行");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug(Thread.currentThread().getName()+"执行结束");

        }, "t1");
        t1.setDaemon(true);
        t1.start();

        TimeUnit.SECONDS.sleep(1);
        log.debug(Thread.currentThread().getName()+"执行结束");
    }
}

