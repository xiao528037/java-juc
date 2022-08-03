package com.xiao.juc.review._03_jmm.modal;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-26 16:44:45
 * @description
 */
@Slf4j(topic = "监控线程")
public class TwoPhaseTermination {

    private Thread monitor;//监控线程
    private volatile boolean mark = true; //标记

    public void startMonitor() {
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
    }

    public void stopMonitor() {
        mark = false;
        monitor.interrupt();
    }
}
