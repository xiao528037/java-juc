package com.xiao.juc.review._06_aqs.countdown;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-08-03 11:12:49
 * @description 只有统计数量达到指定数量是才会继续执行
 */

@Slf4j(topic = "count down")
public class CountDownTest {
    CountDownLatch countDownLatch = new CountDownLatch(10);
    ThreadPoolExecutor threadPool = new ThreadPoolExecutor(10, 10, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new ThreadFactory() {
        AtomicInteger count = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, count.getAndIncrement() + "");
        }
    });
    String[] all = new String[10];
    Random random = new Random();

    @Test
    public void t1() throws InterruptedException {

        for (int i = 0; i < 11; i++) {
            int finalI = i;
            threadPool.submit(() -> {
                for (int j = 0; j <= 100; j++) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(random.nextInt(100));
                        all[finalI] = Thread.currentThread().getName() + "-" + j + "%";
                        log.info("{} ", Arrays.toString(all));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
    }

    @Test
    public void t2() {
        System.out.println(true ? 1 : 2);
    }
}