package com.xiao.juc.review._03_jmm.visibility;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-25 18:46:40
 * @description 测试可见性
 */
@Slf4j(topic = "可见性测试")
public class AppTest {
    static boolean flag = true;

    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            while (flag) {
            }
        }, "t1").start();
        TimeUnit.SECONDS.sleep(1);
        flag = false;
    }

    private volatile int  i = 0;

    @Test
    public void t1() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int j = 0; j < 1000000; j++) {
                i++;
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int j = 0; j < 1000000; j++) {
                i--;
            }
        }, "t2");
        t1.start();
        t2.start();

        t1.join();
        t2.join();
        log.debug("{} ", i);

    }
}
