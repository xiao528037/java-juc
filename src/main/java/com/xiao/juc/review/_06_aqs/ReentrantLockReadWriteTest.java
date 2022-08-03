package com.xiao.juc.review._06_aqs;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-08-02 16:48:03
 * @description
 */
@Slf4j(topic = "read-write")
public class ReentrantLockReadWriteTest {
    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
    private ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

    @Test
    public void t1() throws InterruptedException {
        new Thread(() -> {
            readLock.lock();
            try {
                log.debug("{} {} ", Thread.currentThread().getName(), "读锁...");
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                readLock.unlock();
            }
        }, "t1").start();
        new Thread(() -> {
            readLock.lock();
            try {
                log.debug("{} {} ", Thread.currentThread().getName(), "读锁...");
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                readLock.unlock();
            }
        }, "t2").start();
        TimeUnit.SECONDS.sleep(10);
    }

    @Test
    public void t2() {
        log.debug("{} ", 0 >>> 16);

    }
}
