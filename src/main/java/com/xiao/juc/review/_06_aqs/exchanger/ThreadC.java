package com.xiao.juc.review._06_aqs.exchanger;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-08-03 15:49:36
 * @description
 */

@Slf4j(topic = "thread-c")
public class ThreadC extends Thread {
    private Exchanger<String> exchanger;

    public ThreadC(Exchanger<String> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        try {
            log.debug("{} ","线程c，做好了礼物c，等待线程A送来的礼物A");
            TimeUnit.SECONDS.sleep(3);
            log.debug("线程B收到线程A的礼物"+exchanger.exchange("礼物B"));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
