package com.xiao.juc.learn._01_thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * projectName: java-juc
 * package:com.xiao.juc._01_thread
 * author: aloneMan
 * createTime: 2022-07-09 10:29:44
 * description：非阻塞时打断不会清空打断状态
 */
@Slf4j
public class Thread_interrupt_02 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (true){
                if (Thread.currentThread().isInterrupted()) {
                    log.info("当前线程被打断");
                    break;
                }
            }
        }, "t1");

        t1.start();
        TimeUnit.SECONDS.sleep(1);
        log.info("interrupt");
        t1.interrupt();
    }
}
