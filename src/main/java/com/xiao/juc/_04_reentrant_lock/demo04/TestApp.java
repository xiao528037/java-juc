package com.xiao.juc._04_reentrant_lock.demo04;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-13 16:06:22
 * @description： 题目：使用十个线程，有序打印1-100
 */
@Slf4j
public class TestApp {
    private static Integer i = 1;

    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock(true);
        ArrayList<Thread> threads = new ArrayList<>();
        for (int j = 0; j < 10; j++) {
            Thread thread = new Thread(() -> {
                reentrantLock.lock();
                try {
                    for (int k = 0; k < 10; k++) {
                        log.debug("{}", i++);
                    }
                    log.debug("{} 执行结束...", Thread.currentThread().getName());
                } finally {
                    reentrantLock.unlock();
                }
            }, "t" + j);
            threads.add(thread);
        }
       threads.get(1).start();
        threads.get(0).start();
        threads.get(3).start();
        threads.get(2).start();
        threads.get(4).start();
        threads.get(5).start();
        threads.get(7).start();
        threads.get(6).start();
        threads.get(9).start();
        threads.get(8).start();

    }
}
