package com.xiao.juc.review._06_aqs.CyclicBarrier;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-08-03 14:45:43
 * @description
 */
@Slf4j(topic = "cyclicBarrier")
public class CyclicBarrierTest {
    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 2, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new CustomizeThreadFactory("cyclicBarrierTest"));
    CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> {
        log.debug("{} ", Thread.currentThread().getName());
    });

    @Test
    public void t1() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            threadPoolExecutor.submit(() -> {
                log.debug("{} ", "task 1 begin...");
                try {
                    TimeUnit.SECONDS.sleep(1);
                    cyclicBarrier.await();
                    log.debug("{} ", "task 1 end...");
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });

            threadPoolExecutor.submit(() -> {
                log.debug("{} ", "task 2 begin...");
                try {
                    TimeUnit.SECONDS.sleep(2);
                    cyclicBarrier.await();
                    log.debug("{} ", "task 2 end...");
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
        TimeUnit.SECONDS.sleep(10);
    }
}
