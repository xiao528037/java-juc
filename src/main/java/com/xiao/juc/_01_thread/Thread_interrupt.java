package com.xiao.juc._01_thread;

import lombok.extern.slf4j.Slf4j;

/**
 * projectName: java-juc
 * package:com.xiao.juc._01_thread
 * author: aloneMan
 * createTime: 2022-07-09 08:10:29
 * description：线程阻塞时清空打断状态
 */
@Slf4j
public class Thread_interrupt {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                log.debug("sleep...");
//                Thread.sleep(2000);
                Thread.sleep(5000 );
                log.info("睡完执行... ");
            } catch (InterruptedException e) {
                log.debug("weak up..");
                throw new RuntimeException(e);
            }
        }, "t1");
        t1.start();
        Thread.sleep(3000);
        log.debug("interrupt...");
        t1.interrupt();
        Thread.sleep(1000);
        log.info("是否打断 {}",t1.isInterrupted());

    }
}
