package com.xiao.juc._01_thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * projectName: java-juc
 * package:com.xiao.juc._01_thread
 * author: aloneMan
 * createTime: 2022-07-09 10:59:42
 * description：两阶段终止模式
 */
public class Thread_Two_Phase_Termination {
    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTermination twoPhaseTermination = new TwoPhaseTermination();
        twoPhaseTermination.start();
        TimeUnit.SECONDS.sleep(2);
        twoPhaseTermination.stop();

    }


}
@Slf4j
class TwoPhaseTermination {
    private Thread monitor;

    public void start() {
        monitor = new Thread(() -> {
            while (true) {
                Thread current = Thread.currentThread();
                if (current.isInterrupted()) {
                    log.info("在阻塞时被打断了");
                    break;
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                    log.info("执行监控...");
                } catch (InterruptedException e) {
                    monitor.interrupt();
//                    throw new RuntimeException(e);
                }
            }
        });
        monitor.start();
    }

    public void stop() {
        monitor.interrupt();
    }
}