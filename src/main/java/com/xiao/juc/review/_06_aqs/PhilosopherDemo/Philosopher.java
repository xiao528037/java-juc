package com.xiao.juc.review._06_aqs.PhilosopherDemo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-08-02 00:10:47
 * @description
 */

@Slf4j(topic = "哲学家")
public class Philosopher extends Thread {
    private String name;
    private Condition current;
    private Condition next;

    private ReentrantLock lock;

    public Philosopher(String name, ReentrantLock lock, Condition current, Condition next) {
        this.name = name;
        this.current = current;
        this.next = next;
        this.lock = lock;
    }

    @Override
    public void run() {
        lock.lock();
        try {
            while (true) {
                try {
                    current.await();
                    log.debug("{} 吃饭了...", name);
                    next.signal();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } finally {
            lock.unlock();
        }


    }
}
