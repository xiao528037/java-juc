package com.xiao.juc._04_reentrant_lock;

import lombok.extern.slf4j.Slf4j;

import java.sql.Time;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * projectName: java-juc
 * <p>
 * author: aloneMan
 * <p>
 * createTime: 2022-07-13 08:55:00
 * <p>
 * description：reentrantLock的condition条件变量的基本使用
 */

@Slf4j
public class TestReentrantLockCondition {
    //锁
    private static ReentrantLock lock = new ReentrantLock();

    //标记，外卖和烟是否送达
    private static boolean smokingFlag = false;
    private static boolean takeoutFlag = false;

    public static void main(String[] args) {
        //获取两个条件变量（休息室）
        Condition smokingCondition = lock.newCondition();
        Condition takeoutCondition = lock.newCondition();

        //获取两个线程，一个等烟，一个等外卖
        Thread smokingThread = new Thread(() -> {
            lock.lock();
            try {
                while (!smokingFlag) {
                    log.debug("开始等待香烟送达...");
                    smokingCondition.await();
                }
                log.debug("香烟等到了,开始工作...");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }, "等烟");

        Thread takeoutThread = new Thread(() -> {
            lock.lock();
            try {
                while (!takeoutFlag) {
                    log.debug("开始等待外卖...");
                    takeoutCondition.await();
                }
                log.debug("外卖等到了,吃饱了,开始工作...");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }, "等外卖");
        smokingThread.start();
        takeoutThread.start();

        new Thread(() -> {
            lock.lock();
            try {
                log.debug("开始送香烟...");
                TimeUnit.SECONDS.sleep(2);
                smokingFlag = !smokingFlag;
                smokingCondition.signal();
                log.debug("香烟送达...");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }, "送香烟").start();

        new Thread(() -> {
            lock.lock();
            try {
                log.debug("开始送外卖...");
                takeoutFlag = !takeoutFlag;
                takeoutCondition.signal();
                log.debug("外卖送达...");
            } finally {
                lock.unlock();
            }
        }, "送外卖").start();
    }
}

