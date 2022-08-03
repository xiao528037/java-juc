package com.xiao.juc.review._02_synchronize.wait_notify;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;
import sun.misc.Unsafe;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-24 17:58:11
 * @description 复习wait 和 notify的使用
 */

@Slf4j(topic = "x.wait-notify")
public class WaitNotifyTest {

    private Object lock = new Object();
    AtomicInteger i = new AtomicInteger(0);

    @Test
    public void t1() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                try {
                    log.debug("{} 哦豁，被强制等待了", Thread.currentThread().getName());
                    lock.wait();
                    log.debug("{} 我被唤醒了", Thread.currentThread().getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                try {
                    log.debug("{} 哦豁，被强制等待了", Thread.currentThread().getName());
                    lock.wait();
                    log.debug("{} 我被唤醒了", Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "t2");
        t1.start();
        t2.start();
        Thread.sleep(1000);
        lock.notify();//唤醒一个
        lock.notifyAll();//唤醒全部
    }

    //虚假唤醒的问题，使用信号量来解决
    @Test
    public void t2() {
        //锁
        Object lock = new Object();
        //信号量

        //开启一个等待送烟的外卖
        new Thread(() -> {
            synchronized (lock) {
                while (i.get() != 1) {
                    try {
                        log.debug("{} 进入等待", Thread.currentThread().getName());
                        lock.wait();

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (i.compareAndSet(i.get(), 0)) {
                    log.debug("{} 等到烟了,开始工作...", Thread.currentThread().getName());
                }

            }
        }, "smoking").start();

        new Thread(() -> {
            synchronized (lock) {
                while (i.get() != 2) {
                    log.debug("{} 等待外卖", Thread.currentThread().getName());
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (i.compareAndSet(2, 0)) {
                    log.debug("{} 外卖等到了,开始干饭", Thread.currentThread().getName());

                }
            }
        }, "takeout").start();

        new Thread(() -> {
            while (!i.compareAndSet(0, 1)) {
                log.debug("烟没送到,等会再送...");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            synchronized (lock) {
                lock.notifyAll();
            }

            log.debug("{} 烟送到了", Thread.currentThread().getName());
        }, "takeaway_one").start();
        new Thread(() -> {
            while (!i.compareAndSet(0, 2)) {
                log.debug("外卖没送到,等会再送...");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            synchronized (lock) {
                lock.notifyAll();
            }
            log.debug("{} 外卖送到了", Thread.currentThread().getName());
        }, "takeaway_two").start();
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //通过CAS实现线程安全
    @Test
    public void t3() throws InterruptedException {
        AtomicInteger count = new AtomicInteger(0);
        Long begin = System.currentTimeMillis();
        //创建一百个线程，每个线程累加1000次
        for (int j = 0; j < 100; j++) {
            new Thread(() -> {
                for (int k = 0; k < 10000; k++) {
                    log.debug("{}", count.incrementAndGet());
                }
            }).start();
        }
        Long useTime = System.currentTimeMillis() - begin;

        Thread.sleep(10000);

    }

    @Test
    public void t4() {
        String str = "我爱你";
        String st2 = str;
        String newStr = st2.replace('我', '你');
        log.debug("{} {}", str == st2, str == newStr);
    }
}
