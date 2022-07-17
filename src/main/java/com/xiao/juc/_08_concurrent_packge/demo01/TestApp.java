package com.xiao.juc._08_concurrent_packge.demo01;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-16 19:28:38
 * @description
 */
@Slf4j
public class TestApp {
    @Test
    public void test1() throws InterruptedException {
        Thread.sleep(20000);
        ThreadPools<Task> taskThreadPools = new ThreadPools<>();
        for (int i = 0; i < 400000; i++) {
            int j = i;

            taskThreadPools.execute(new Task(() -> j + 1, x -> x));
        }
        Thread.sleep(1000);
    }

    @Test
    public void BlockQueueTet() throws InterruptedException {
        BlockingQueue<Task> taskBlockingQueue = new BlockingQueue<>();

        new Thread(() -> {
            for (int i = 0; i < 50000; i++) {
                int j = i;
                new Thread(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    taskBlockingQueue.setTask(new Task(() -> j, x -> x));
                }).start();
            }
        }).start();

        new Thread(() -> {
            while (true) {
                taskBlockingQueue.getTask();
            }

        }).start();
        Thread.sleep(1000);
    }
}
