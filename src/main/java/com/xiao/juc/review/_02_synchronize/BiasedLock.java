package com.xiao.juc.review._02_synchronize;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-23 13:01:56
 * @description 重新测试批量撤销偏向锁和批量重偏向
 */

@Slf4j(topic = "x.biased")
public class BiasedLock {
    private static ReentrantLock reentrantLock = new ReentrantLock();
    private static Condition condition = reentrantLock.newCondition();
    private static List<Object> locks = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        //开启一个线程，将锁偏向t1
        Thread t1 = new Thread(() -> {
            //创建三十个锁对象，偏向t1
            for (int i = 0; i < 40; i++) {
                Object o = new Object();
                locks.add(o);
                synchronized (o) {
                    log.debug("{} 偏向t1线程 \n{}", i, ClassLayout.parseInstance(o).toPrintable());
                }
            }
            reentrantLock.lock();
            try {
                condition.signal();
            } finally {
                reentrantLock.unlock();
            }

        }, "t1");

        //开启一个线程，批量撤销偏向锁，达到20次后开始重新偏向t2线程
        Thread t2 = new Thread(() -> {
            reentrantLock.lock();
            try {
                //防止在t1线程之前执行
                condition.await();
                for (int i = 0; i < 40; i++) {
                    Object o = locks.get(i);
                    synchronized (o) {
                       /* if (i < 18)
                            log.debug("{} 锁批量撤销 \n{}", i, ClassLayout.parseInstance(o).toPrintable());
                        else  {
                            log.debug("{} 批量重偏向 \n{}", i, ClassLayout.parseInstance(o).toPrintable());
                        }*/

                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                reentrantLock.unlock();
            }
        }, "t2");
        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                Object o = locks.get(i);
                synchronized (o) {
                    if (i < 18)
                        log.debug("{} 锁批量撤销 \n{}", i, ClassLayout.parseInstance(o).toPrintable());
                    else {
                        log.debug("{} 批量重偏向 \n{}", i, ClassLayout.parseInstance(o).toPrintable());
                    }
                }
            }
        }, "t3");
        t1.start();

        t2.start();
        t1.join();
        t2.join();

        t3.start();
        t3.join();
        log.debug("{}", ClassLayout.parseInstance(locks.get(0)).toPrintable());
        log.debug("{}", ClassLayout.parseInstance(locks.get(17)).toPrintable());
        log.debug("{}", ClassLayout.parseInstance(locks.get(18)).toPrintable());
        log.debug("{}", ClassLayout.parseInstance(locks.get(19)).toPrintable());
        Object o = new Object();
        synchronized (o) {
            log.debug("{}", ClassLayout.parseInstance(o).toPrintable());
        }
    }
}
