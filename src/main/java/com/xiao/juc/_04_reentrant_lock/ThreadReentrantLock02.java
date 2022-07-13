package com.xiao.juc._04_reentrant_lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * projectName: java-juc
 * <p>
 * author: aloneMan
 * <p>
 * createTime: 2022-07-12 18:37:18
 * <p>
 * description：lockInterruptibly()尝试获取锁，并且可以打断，但是只能够被动打断，能够防止死锁的发生
 */
@Slf4j
public class ThreadReentrantLock02 {
    private static ReentrantLock reentrantLock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                log.debug("{} 尝试获得锁", Thread.currentThread().getName());
                reentrantLock.lockInterruptibly();
            } catch (InterruptedException e) {
                log.debug("{} 获取锁失败", Thread.currentThread().getName());
                return;
            }
            try {
                log.debug("{} 线程获得了锁", Thread.currentThread().getName());
            } finally {
                reentrantLock.unlock();
            }

        }, "t1");

        Thread t2 = new Thread(() -> {
            try {
                log.debug("{} 尝试获得锁", Thread.currentThread().getName());
                reentrantLock.lockInterruptibly();
            } catch (InterruptedException e) {
                log.debug("{} 获取锁失败", Thread.currentThread().getName());
                return;
            }
            try {
                log.debug("{} 线程获得了锁", Thread.currentThread().getName());
            } finally {
                reentrantLock.unlock();
            }
        }, "t2");
        reentrantLock.lock();
        t1.start();
        t2.start();
        t1.interrupt();
    }
}
