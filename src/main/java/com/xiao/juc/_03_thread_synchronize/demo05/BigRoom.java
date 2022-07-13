package com.xiao.juc._03_thread_synchronize.demo05;

import lombok.extern.slf4j.Slf4j;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * projectName: java-juc
 * <p>
 * author: aloneMan
 * <p>
 * createTime: 2022-07-12 16:39:19
 * <p>
 * description：大房间
 */

@Slf4j
public class BigRoom {

    private Object a = new Object();
    private Object b = new Object();

    public void studyRoom() throws InterruptedException {
        synchronized (a) {
            log.debug("{} 得到锁A", Thread.currentThread().getName());
            TimeUnit.SECONDS.sleep(1);
            synchronized (b) {
                log.debug("{} 得到锁B", Thread.currentThread().getName());
            }
        }
    }

    public void bedRoom() throws InterruptedException {
        synchronized (b) {
            log.debug("{} 得到锁B", Thread.currentThread().getName());
            TimeUnit.SECONDS.sleep(2);
            synchronized (a) {
                log.debug("{} 得到锁A", Thread.currentThread().getName());
            }
        }
    }
}
