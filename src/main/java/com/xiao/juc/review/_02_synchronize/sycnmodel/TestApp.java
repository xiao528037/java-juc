package com.xiao.juc.review._02_synchronize.sycnmodel;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-24 21:16:54
 * @description
 */

@Slf4j(topic = "保护性暂停")
public class TestApp {
    @Test
    public void t1() throws InterruptedException {
        GuardedObject<String> guardedObject = new GuardedObject<>();
        Thread t1 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                guardedObject.put(Arrays.asList("xiao", "jie", "bin", "消", "消", "汽"));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            log.debug("获取的数组是 {}", guardedObject.get(2000L));
        }, "t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

    @Test
    public void t2() throws InterruptedException {
        Object lock = new Object();
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                try {
                    log.debug("我阻塞了");
                    lock.wait();
                    log.debug("我执行了");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                lock.notifyAll();
            }
        }, "t2");

        t1.start();
        TimeUnit.SECONDS.sleep(2);
        t2.start();

        t2.join();
        t1.join();
    }
}
