package com.xiao.juc._04_reentrant_lock.demo01;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * projectName: java-juc
 * <p>
 * author: aloneMan
 * <p>
 * createTime: 2022-07-12 19:37:03
 * <p>
 * description：哲学家
 */

@Slf4j
public class Philosopher extends Thread {
    private String name;
    private ReentrantLock right;
    private ReentrantLock left;

    public Philosopher(String name, ReentrantLock right, ReentrantLock left) {
        super(name);
        this.name = name;
        this.right = right;
        this.left = left;
    }

    @Override
    public void run() {
        while (true) {
            if (right.tryLock()) {
                try {
                    if (left.tryLock()) {
                        try {
                            eat();
                        } finally {
                            left.unlock();
                        }
                    }
                } finally {
                    right.unlock();
                }
            }

        }
    }

    Random random = new Random();

    private void eat() {
        log.debug("{} 开始吃饭...", name);
        try {
            TimeUnit.SECONDS.sleep(random.nextInt(1));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
