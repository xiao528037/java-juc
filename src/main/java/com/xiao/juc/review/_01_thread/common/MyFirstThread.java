package com.xiao.juc.review._01_thread.common;

import lombok.extern.slf4j.Slf4j;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-21 16:23:15
 * @description 实现一个线程类
 */
@Slf4j(topic = "c.review.firstThread")
public class MyFirstThread extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            log.debug("线程输出 {}", i);
        }
    }
}
