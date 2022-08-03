package com.xiao.juc.learn._04_reentrant_lock.demo02;

import lombok.extern.slf4j.Slf4j;

/**
 * projectName: java-juc
 * <p>
 * author: aloneMan
 * <p>
 * createTime: 2022-07-13 09:29:37
 * <p>
 * description：使用wait和notify/notifyAll的方式进行顺序打印
 */

@Slf4j
public class TestApp_01 {
    private static Object o = new Object();
    private static boolean flag = false;

    public static void main(String[] args) {

        new Thread(() -> {
            synchronized (o) {
                while (!flag) {
                    try {
                        o.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                log.debug("11111111");
            }
        }, "t1").start();
        new Thread(() -> {
            synchronized (o) {
                log.debug("22222222");
                flag = true;
                o.notifyAll();
            }
        }, "t2").start();
    }
}
