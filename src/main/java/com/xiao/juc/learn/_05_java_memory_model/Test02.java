package com.xiao.juc.learn._05_java_memory_model;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-13 18:29:18
 * @description：
 */
@Slf4j
public class Test02 {
    public static void main(String[] args) throws InterruptedException {
        TwoStageModel twoStageModel = new TwoStageModel();
        twoStageModel.start();
        sleep(20000);
        twoStageModel.stop();
    }
}

@Slf4j
class TwoStageModel {
    private Thread monitorThread;
    private volatile boolean stop;

    public void start() {
        monitorThread = new Thread(() -> {
            while (true) {
                if (stop) {
                    log.debug("停止运行...");
                    break;
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                    log.debug("执行...");
                } catch (InterruptedException e) {
                }
            }
        });

        monitorThread.start();

    }

    public void stop() {
        stop = true;
        monitorThread.interrupt();
    }
}