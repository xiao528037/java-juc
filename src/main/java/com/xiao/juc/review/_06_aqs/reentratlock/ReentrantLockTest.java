package com.xiao.juc.review._06_aqs.reentratlock;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-08-01 17:02:05
 * @description
 */
@Slf4j(topic = "reentrantLock")
public class ReentrantLockTest {
    private ReentrantLock lock = new ReentrantLock();

    @Test
    public void t1() {
        lock.lock();
        try {
            t2();
        } finally {
            lock.unlock();
        }

    }

    public void t2() {
        lock.lock();
        try {
            log.debug("{} ", "method t2()");
        } finally {
            lock.unlock();
        }

    }

    @Test
    public void t3() throws InterruptedException {
        Thread interrupt = new Thread(() -> {
            try {
                log.debug("{} ", "尝试获取锁...");
                lock.lockInterruptibly();
                try {
                    log.debug("{} ", "获取到锁...");
                } finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                log.debug("{} ", "没有获取到锁，被打断，直接返回");
                return;
            }
        }, "interrupt_test");
        lock.lock();
        try {
            interrupt.start();
            TimeUnit.SECONDS.sleep(2);
            log.debug("{} ", "开始打断interrupt_test线程");
            interrupt.interrupt();
        } finally {
            lock.unlock();
        }
    }

    @Test
    public void t4() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                log.debug("{} ", "等待10秒");
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            boolean b = false;
            try {
                b = lock.tryLock(2, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                log.debug("{} ", "等待锁时被打断");
            } finally {

                lock.unlock();
            }
        }, "t2");

        t1.start();
        t2.start();

        TimeUnit.SECONDS.sleep(11);

    }
}
