package com.xiao.juc.learn._01_thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * projectName: java-juc
 * package:com.xiao.juc._01_thread
 * author: aloneMan
 * createTime: 2022-07-09 09:43:56
 * description：等待线程执行完毕
 */
@Slf4j
public class Thread_Join {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "t2");
        t1.start();
        t2.start();
        log.info("wait begin...");
        log.info("t1 join...");
        t1.join();
        log.info("t2.join...");
        t2.join();
        log.info("wait end...");
    }
}
