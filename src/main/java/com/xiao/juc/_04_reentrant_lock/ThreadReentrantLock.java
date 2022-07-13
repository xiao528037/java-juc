package com.xiao.juc._04_reentrant_lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * projectName: java-juc
 * <p>
 * author: aloneMan
 * <p>
 * createTime: 2022-07-12 18:08:55
 * <p>
 * description：证明reentrantLock可以重入
 */
@Slf4j
public class ThreadReentrantLock {
    private static ReentrantLock reentrantLock = new ReentrantLock();

    public static void main(String[] args) {
        reentrantLock.lock();
        try {
            log.debug("main方法执行..");
            m1();
        } finally {
            reentrantLock.unlock();
        }
    }

    private static void m1() {
        reentrantLock.lock();
        try {
            log.debug("m1方法执行...");
            m2();
        } finally {
            reentrantLock.unlock();
        }
    }

    private static void m2() {
        reentrantLock.lock();
        try {
            log.debug("m2方法执行...");
            m3();

        } finally {
            reentrantLock.unlock();
        }
    }

    private static void m3() {
        reentrantLock.lock();
        try {
            log.debug("m3方法执行....");
        } finally {
            reentrantLock.unlock();
        }
    }
}
