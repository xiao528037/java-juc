package com.xiao.juc.learn._03_thread_synchronize;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * projectName: java-juc
 * package:com.xiao.juc._03_thread_synchronize
 * author: aloneMan
 * createTime: 2022-07-09 20:25:49
 * description：
 */
@Slf4j
public class ParallelBug {
    static AtomicInteger atomicInteger = new AtomicInteger(100);


    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            while (atomicInteger.getAndDecrement() > 0) {
                log.info("购买票 {} 张", atomicInteger.get());
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            while (atomicInteger.getAndDecrement() > 0) {
                log.info("购买票 {} 张", atomicInteger.get());
            }
        }, "t2");
        t2.start();
        t1.start();

        System.out.println(atomicInteger.get());
    }
}
