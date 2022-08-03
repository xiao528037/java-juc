package com.xiao.juc.review._06_aqs.exchanger;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-08-03 15:54:56
 * @description
 */
@Slf4j(topic = "thread-a")
public class ThreadA extends Thread {
    private Exchanger<String> exchanger;

    public ThreadA(Exchanger<String> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        try {
            log.debug("{} ", "线程A，做好了礼物A，等待线程A送来的礼物A");
            String s = exchanger.exchange("礼物A", 5, TimeUnit.SECONDS);
            log.debug("线程A收到线程b的礼物" +s);
        } catch (InterruptedException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
