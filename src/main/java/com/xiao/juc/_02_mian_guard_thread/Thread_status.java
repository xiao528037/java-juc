package com.xiao.juc._02_mian_guard_thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * projectName: java-juc
 * package:com.xiao.juc._02_mian_guard_thread
 * author: aloneMan
 * createTime: 2022-07-09 12:02:05
 * description：
 */
@Slf4j
public class Thread_status {

    public static void main(String[] args) throws InterruptedException {
        //新建
        Thread t1 = new Thread(() -> {
        }, "t1");

        //可运行状态
        Thread t2 = new Thread(() -> {
            while (true) {

            }
        }, "t2");
        t2.start();

        //结束状态
        Thread t3 = new Thread(() -> {
            log.info("执行结束");
        }, "t3");
        t3.start();

        //timed waiting
        Thread t4 = new Thread() {
            @Override
            public synchronized void run() {
                synchronized (Thread_status.class) {
                    try {
                        TimeUnit.SECONDS.sleep(1000000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
        t4.start();

        //waiting
        Thread t5 = new Thread() {
            @Override
            public void run() {
                try {
                    t2.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        t5.start();

        //blocked
        Thread t6 = new Thread() {
            @Override
            public void run() {
                synchronized (Thread_status.class) {
                    log.info("t4执行");
                }
            }
        };
        t6.start();

        TimeUnit.SECONDS.sleep(5);
        log.info("t1 status {}",t1.getState());
        log.info("t2 status {}",t2.getState());
        log.info("t3 status {}",t3.getState());
        log.info("t4 status {}",t4.getState());
        log.info("t5 status {}",t5.getState());
        log.info("t6 status {}",t6.getState());
    }
}
