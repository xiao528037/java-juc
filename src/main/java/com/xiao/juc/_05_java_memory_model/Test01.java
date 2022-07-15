package com.xiao.juc._05_java_memory_model;

import lombok.extern.slf4j.Slf4j;

import static java.lang.Thread.sleep;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-13 17:59:14
 * @description：
 */
@Slf4j
public class Test01 {
    private volatile static boolean run = true;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (run) {

            }
        }).start();
        sleep(1000);
        log.debug("停止运行");
        run = false;
        log.debug("{}",run);

    }
}
