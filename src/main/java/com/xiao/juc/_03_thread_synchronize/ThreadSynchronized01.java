package com.xiao.juc._03_thread_synchronize;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * projectName: java-juc
 * package:com.xiao.juc._03_thread_synchronize
 * author: aloneMan
 * createTime: 2022-07-09 21:18:33
 * description：
 */

@Slf4j
public class ThreadSynchronized01 {
    public static void main(String[] args) throws InterruptedException {
        CountAction countAction = new CountAction();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                log.info("剩余票数为 {}",countAction.increment());
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                log.info("剩余票数为 {}",countAction.decrement());
            }

        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.info("剩余值 {}", countAction.getCount());
    }
}

class CountAction {

    private static AtomicInteger countAtomicInt = new AtomicInteger(0);
    private static int count = 0;

    public int increment() {
        synchronized (this) {
            count++;
            return count;
        }
    }

    public int decrement() {
        synchronized (this) {

                count--;

            return count;
        }
    }

    public int getCount() {
        synchronized (this) {
            return count;
        }
    }
}