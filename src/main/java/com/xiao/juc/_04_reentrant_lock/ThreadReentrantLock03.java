package com.xiao.juc._04_reentrant_lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;


/**
 * projectName: java-juc
 * <p>
 * author: aloneMan
 * <p>
 * createTime: 2022-07-12 19:21:37
 * <p>
 * description：tryLock尝试获取锁，如果获取不到主动打断当前线程
 */
@Slf4j
public class ThreadReentrantLock03 {
    private static ReentrantLock reentrantLock = new ReentrantLock();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.debug("{} 尝试获取锁...", Thread.currentThread().getName());
            try {
                if (!reentrantLock.tryLock(2, TimeUnit.SECONDS)) {
                    log.debug("{} 获取锁失败...", Thread.currentThread().getName());
                    return;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                log.debug("{} 获取锁成功...", Thread.currentThread().getName());
            }finally {
                reentrantLock.unlock();
            }

        }, "t1");
        t1.start();
        reentrantLock.lock();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            reentrantLock.unlock();
        }

    }
}
