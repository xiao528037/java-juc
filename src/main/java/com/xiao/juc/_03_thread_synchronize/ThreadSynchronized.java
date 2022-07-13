package com.xiao.juc._03_thread_synchronize;

import lombok.extern.slf4j.Slf4j;

/**
 * projectName: java-juc
 * package:com.xiao.juc._03_thread_synchronize
 * author: aloneMan
 * createTime: 2022-07-09 20:45:38
 * description：
 */

@Slf4j

public class ThreadSynchronized {

    private static int count = 100;

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (ThreadSynchronized.class) {
                while (count > 0) {
                    log.info("当前票剩余 {}", count--);
                }
            }
        }, "t1").start();
        new Thread(() -> {
            synchronized (ThreadSynchronized.class) {
                while (count > 0) {
                    log.info("当前票剩余 {}", count--);
                }
            }
        }, "t1").start();

    }
}
