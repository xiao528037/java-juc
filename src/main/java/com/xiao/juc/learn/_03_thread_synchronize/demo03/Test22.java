package com.xiao.juc.learn._03_thread_synchronize.demo03;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * projectName: java-juc
 * <p>
 * author: aloneMan
 * <p>
 * createTime: 2022-07-11 15:22:42
 * <p>
 * description：消息队列测试
 */
@Slf4j
public class Test22 {
    public static void main(String[] args) throws InterruptedException {
        MessageQueue<String> queue = new MessageQueue<>(10);
        AtomicInteger count = new AtomicInteger(0);
        for (int i = 0; i < 1000; i++) {
            int id = i;
            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.debug("开始存入 >> {} << 对象", count.incrementAndGet());
                queue.put(new Message<>(id, "我的消息是 >>>  " + id));
            }, "线程" + id).start();
        }
        /*new Thread(() -> {
            while (true) {
                Message<String> take = queue.take();

                log.debug("id是 {} - 消息是 {}", take.getId(), take.getMessage());
            }
        }, "get-message-thread").start();*/

        new Thread(() -> {
            while (true) {

                System.out.println(queue.take().toString());
            }
        }).start();
        TimeUnit.SECONDS.sleep(10);

            new Thread(()->{
                queue.put(new Message<>(111, "我的消息是 >>>  " + "asdfjasldfkjadlfjas;dlfja;sdfkj"));
            }).start();
    }
}
