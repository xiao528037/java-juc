package com.xiao.juc._04_reentrant_lock.demo02;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * projectName: java-juc
 * <p>
 * author: aloneMan
 * <p>
 * createTime: 2022-07-13 14:19:56
 * <p>
 * description：
 */
public class Test_06_reentrantlock {

    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition c1 = reentrantLock.newCondition();
        WaitNotify03<String> stringWaitNotify03 = new WaitNotify03<>(1, 5, 4, c1);

        new Thread(() -> {
            reentrantLock.lock();
            try {
                stringWaitNotify03.print("A", 1);
            } finally {
                reentrantLock.unlock();
            }

        }, "t1").start();
        new Thread(() -> {
            reentrantLock.lock();
            try {
                stringWaitNotify03.print("B", 2);
            } finally {
                reentrantLock.unlock();
            }
        }, "t2").start();
        new Thread(() -> {
            reentrantLock.lock();
            try {
                stringWaitNotify03.print("C", 3);
            } finally {
                reentrantLock.unlock();
            }
        }, "t3").start();
        new Thread(() -> {
            reentrantLock.lock();
            try {
                stringWaitNotify03.print("D", 4);
            } finally {
                reentrantLock.unlock();
            }
        }, "t4").start();
    }
}

@Slf4j
class WaitNotify03<T> {


    public void print(T t, Integer current) {
        //打印的次数
        for (int i = 0; i < loopNumber; i++) {
            while (!flag.equals(current)) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if (flag == 1) {
                log.debug("----------");
            }
            log.debug("{}", t);
            flag = (current % printSize) + 1;
            condition.signalAll();

        }
    }

    private Integer flag;
    private Integer loopNumber;
    private Integer printSize;
    private Condition condition;

    public WaitNotify03(Integer flag, Integer loopNumber, Integer printSize, Condition condition) {
        this.flag = flag;
        this.loopNumber = loopNumber;
        this.printSize = printSize;
        this.condition = condition;
    }
}
