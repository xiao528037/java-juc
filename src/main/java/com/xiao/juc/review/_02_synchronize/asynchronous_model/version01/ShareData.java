package com.xiao.juc.review._02_synchronize.asynchronous_model.version01;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-25 12:12:34
 * @description
 */

@Slf4j(topic = "消息库")
public class ShareData {
    private int number = 0;
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void increment() {
        lock.lock();
        try {
            while (number != 0) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            log.debug("{} ", number--);
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    public void decrement() {
        lock.lock();
        try {
            while (number == 1) {
                condition.await();
            }
            log.debug("{} ", number++);
            condition.signal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }

    }
}
