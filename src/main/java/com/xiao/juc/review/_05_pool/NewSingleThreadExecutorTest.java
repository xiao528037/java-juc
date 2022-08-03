package com.xiao.juc.review._05_pool;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-28 20:37:16
 * @description
 */
@Slf4j(topic = "可扩容的线程池")
public class NewSingleThreadExecutorTest {

    @Test
    public void t1() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10), new ThreadFactory() {

            private AtomicInteger count = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "我自己定义个一个线程工厂" + count.getAndIncrement());
            }
        });
        threadPoolExecutor.execute(() -> {
            log.debug("{} ", "我是谁！");
        });

        AtomicInteger atomicInteger = new AtomicInteger(1);

        Future<Integer> submit = threadPoolExecutor.submit(() -> {
            log.debug(">>>");
            return 1;
        });
        Future<AtomicInteger> submit1 = threadPoolExecutor.submit(() -> atomicInteger.incrementAndGet(), new AtomicInteger());
        log.debug("{} ", submit.get());
        log.debug("{}", submit1.get());
        ExecutorService pool = Executors.newFixedThreadPool(10);
        pool.submit(() -> {
            log.debug("test");
        });

    }

    @Test
    public void t2() {
        flag:
        for (int i = 0; i < 1000; i++) {
            if (i == 10) {
                i = 100;
            }
            for (int j = 0; j < 1000; j++) {
                if (j == 100 && i < 100) {
                    continue flag;
                }
                if (j == 10) {
                    break flag;
                }
                if (j == 300) {
                    System.out.println("00");
                }
            }
        }

        log.debug(">>>>");
    }

}
