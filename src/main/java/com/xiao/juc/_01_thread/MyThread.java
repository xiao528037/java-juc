package com.xiao.juc._01_thread;

import lombok.extern.slf4j.Slf4j;

/**
 * projectName: java-juc
 * package:com.xiao.juc._01_thread
 * author: aloneMan
 * createTime: 2022-07-08 21:16:36
 * description：
 */

@Slf4j(topic = "c.MyThread")
public class MyThread {
    public static void main(String[] args) {
        new Thread() {
            int count = 1;

            @Override
            public void run() {
                while (true) {
                    log.info(Thread.currentThread().getName() + count++);
                }
            }
        }.start();
//        while (true) {
//            log.info(Thread.currentThread().getName());
//        }
        System.out.println();
        new Thread(() -> {
            while (true)
                log.info(Thread.currentThread().getName() + " >>>> ");
        }).start();

    }
}
