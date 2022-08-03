package com.xiao.juc.learn._08_concurrent_packge.demo05;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-21 11:50:53
 * @description
 */
@Slf4j
public class SemaphoreTest {
    int a = 0;

    @Test
    public void t1() throws InterruptedException {

        Semaphore semaphore = new Semaphore(3);
        ReentrantLock reentrantLock = new ReentrantLock();
        for (int i = 0; i < 100000; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    reentrantLock.lock();
                    log.debug("{}", a++);
                    semaphore.release();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    reentrantLock.unlock();
                }
            }, "t-" + i).start();
        }
        Thread.sleep(1500);
        log.debug("{}", a);
    }

    @Test
    public void t2() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        for (int i = 0; i < 4; i++) {
            int j = i;
            new Thread(() -> {
                log.debug("{} 开始执行,等待{}秒...", Thread.currentThread().getName(), j);
                try {
                    Thread.sleep(j * 1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                countDownLatch.countDown();
                log.debug("{} 执行结束...", Thread.currentThread().getName());
            }).start();
        }
        log.debug("开始等待所有线程执行完成...");
        countDownLatch.await();
        log.debug("线程全部执行结束...");
        Thread.sleep(5000);
    }

}
