package com.xiao.juc.review._02_synchronize.sycnmodel;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-24 21:13:57
 * @description
 */
@Slf4j(topic = "x.guarded")
public class GuardedObject<T> {
    private List<T> values = new ArrayList<>();

    private Object lock = new Object();

    public List<T> get(Long millis) {
        synchronized (lock) {
            Long begin = System.currentTimeMillis();//进入方法的时间
            Long timePassed = 0L;
            while (values == null || values.size() == 0) {
                Long waitTime = millis - timePassed;
                if (waitTime <= 0) {
                    log.debug("{} 等待超时", waitTime);
                    break;
                }
                try {
                    lock.wait(waitTime);
                } catch (InterruptedException e) {
                    log.debug("{} 被唤醒...");
                }
                timePassed = System.currentTimeMillis() - begin;
            }
            return values;
        }
    }

    public void put(List<T> newValues) {
        synchronized (lock) {
            this.values = newValues;
            lock.notifyAll();
        }
    }
}
