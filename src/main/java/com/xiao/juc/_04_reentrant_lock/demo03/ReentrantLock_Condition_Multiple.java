package com.xiao.juc._04_reentrant_lock.demo03;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-13 15:42:14
 * @description 使用多个condition来顺序打印, 最开始将所有的线程放入指定的condition中等待，然后在主线程中把头一个线程进行唤醒
 */
public class ReentrantLock_Condition_Multiple {

    public static void main(String[] args) throws InterruptedException {
        WaitNotify<String> waitNotify = new WaitNotify(3);
        Condition condition1 = waitNotify.newCondition();
        Condition condition2 = waitNotify.newCondition();
        Condition condition3 = waitNotify.newCondition();
         new Thread(() -> {
            waitNotify.print("A", condition1, condition2);
        }, "t1").start();
        new Thread(() -> {
            waitNotify.print("B", condition2, condition3);
        }, "t2").start();
        new Thread(() -> {
            waitNotify.print("C", condition3, condition1);
        }, "t3").start();

        TimeUnit.SECONDS.sleep(2);
        waitNotify.lock();
        try {
            condition1.signal();
        } finally {
            waitNotify.unlock();
        }
    }
}

@Slf4j
class WaitNotify<T> extends ReentrantLock {

    /**
     * @param content
     *         打印的内容
     * @param current
     *         当前的休息室
     * @param nextExecute
     *         唤醒的休息室
     */
    public void print(T content, Condition current, Condition nextExecute) {
        for (int i = 0; i < loopNumber; i++) {
            lock();
            try {
                //进入等待
                current.await();
                //等待结束执行打印
                log.debug("{}", content);
                //唤醒下一个线程
                nextExecute.signal();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                unlock();
            }
        }
    }

    //打印次数
    private Integer loopNumber;

    public WaitNotify(Integer loopNumber) {
        this.loopNumber = loopNumber;
    }
}