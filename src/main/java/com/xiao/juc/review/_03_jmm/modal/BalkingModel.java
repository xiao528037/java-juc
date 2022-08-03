package com.xiao.juc.review._03_jmm.modal;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-26 16:57:14
 * @description
 */
@Slf4j(topic = "犹豫")
public class BalkingModel {
    private static volatile boolean mark = false;

    private Thread monitor;

    public void start() {
        synchronized (this) {
            if (mark) {//判断下线程是否创建
                log.debug("{} 监控线程已经创建...", Thread.currentThread().getName());
                return;
            }
            mark = true;
        }
        monitor = new Thread(() -> {
            while (mark) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    log.debug("{} 监控中...", Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    log.debug("{} 监控等待，被打断...", Thread.currentThread().getName());
                }
            }
            log.debug("{} 监控结束...");
        }, "monitor");

        monitor.start();
        log.debug("{} 监控线程创建成功", Thread.currentThread().getName());
    }

    public void stop() {
        mark = false;
        monitor.interrupt();
    }
}
