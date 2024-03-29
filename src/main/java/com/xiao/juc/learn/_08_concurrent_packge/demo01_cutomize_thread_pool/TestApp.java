package com.xiao.juc.learn._08_concurrent_packge.demo01_cutomize_thread_pool;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-16 19:28:38
 * @description
 */
@Slf4j(topic = "c.test")
public class TestApp {
    @Test
    public void test1() throws InterruptedException {
//        Thread.sleep(20000);
        ThreadPools<Task> taskThreadPools = new ThreadPools<>(1, 2, (queue, task) -> {
            log.debug("拒绝策略");
//            queue.setTask(task);
            queue.setTask(task, 3000L);
        });
        for (int i = 0; i < 5; i++) {
            int j = i;

            taskThreadPools.execute(new Task(() -> j + 1, x -> {
                log.debug("ture out {}", x);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return x;
            }));

        }
        Thread.sleep(5000);
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
