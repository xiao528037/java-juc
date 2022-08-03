package com.xiao.juc.review._06_aqs.semaphore;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-08-03 10:27:13
 * @description
 */
@Slf4j(topic = "semaphore")
public class SemaphoreTest {
    @Test
    public void t1() throws InterruptedException {
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    log.debug("{} ", "running...");
                    TimeUnit.SECONDS.sleep(2);
                    log.debug("{} ", "execution end...");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }finally {
                    semaphore.release();
                }
            }, "t" + i).start();
        }

        TimeUnit.SECONDS.sleep(10);
    }
}
