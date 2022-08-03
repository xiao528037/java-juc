package com.xiao.juc.learn._04_reentrant_lock.demo02;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * projectName: java-juc
 * <p>
 * author: aloneMan
 * <p>
 * createTime: 2022-07-13 10:47:41
 * <p>
 * description：
 */

@Slf4j
public class TestApp_03 {
    private static ReentrantLock lock = new ReentrantLock();
    private static boolean flag = false;

    public static void main(String[] args) {
        Condition c1 = lock.newCondition();

        new Thread(() -> {
            lock.lock();
            try {
                while (!flag) {
                    c1.await();
                }
                log.debug("1111");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }, "t1").start();
        new Thread(() -> {
            lock.lock();
            try {
                while (!flag) {
                    log.debug("2222");
                    flag = true;
                    c1.signal();
                }
            } finally {
                lock.unlock();
            }
        }, "t2").start();
    }
}
