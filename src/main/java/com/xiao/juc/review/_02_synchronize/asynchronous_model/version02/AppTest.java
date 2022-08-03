package com.xiao.juc.review._02_synchronize.asynchronous_model.version02;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-25 14:39:27
 * @description
 */
@Slf4j(topic = "测试")
public class AppTest {
    @Test
    public void t1() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        MessageQueue<String> messageQueue = new MessageQueue<>(10);
        Thread t1 = new Thread(() -> {
            while (true) {
                String message = messageQueue.getMessage(2000L);
                log.debug("{} ", message);
            }
        }, "t1");


        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                while (true) {
                    messageQueue.setMessage("1111", 1000L);
                }
            }).start();
        }

        t1.start();
        t1.join();

    }
}
