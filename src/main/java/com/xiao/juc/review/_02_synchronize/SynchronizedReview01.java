package com.xiao.juc.review._02_synchronize;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.xiao.juc.review._02_synchronize.SonSync.*;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-22 16:49:00
 * @description 复习synchronized基础使用
 */
@Slf4j
public class SynchronizedReview01 {

    static int count = 0;
    static Object lock = new Object();

    @Test
    public void sysnBlock() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                count++;
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                count--;
            }
        }, "t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("计算结果 {}", count);
    }

    @Test
    public void sysMethod() throws InterruptedException {
        SonSync sonSync = new SonSync();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                sonSync.increment();
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                sonSync.decrement();
            }
        }, "t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        Integer count1 = sonSync.getCount();
        log.debug("{}", count1);

    }

    @Test
    public void syncStaticMethod() throws InterruptedException {

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                sonIncrement();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                sonDecrement();
            }
        }, "t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("{}", getSonCount());
    }

    @Test
    public void biasLock() throws InterruptedException {
        //jdk1.8偏向锁，启动后四秒中开启
        TimeUnit.SECONDS.sleep(5);
        //创建一百个偏向线程t1的偏向锁
        List<Object> objects = new ArrayList<>();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                Object lock = new Object();
                synchronized (lock) {
                    objects.add(lock);
                }
            }
            try {
                TimeUnit.DAYS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        t1.start();
        //睡眠3s保证线程t1创建对象完成
        TimeUnit.SECONDS.sleep(3);
        log.debug("打印t1线程，list中第19个对象的对象头:");
        log.debug(ClassLayout.parseInstance(objects.get(18)).toPrintable());
        log.debug("打印t1线程，list中第20个对象的对象头:");
        log.debug(ClassLayout.parseInstance(objects.get(19)).toPrintable());
//创建线程t2竞争线程t1中已经退出同步块的锁
        Thread t2 = new Thread(() -> {
            //这里面只循环了30次！！！
            for (int i = 0; i < 30; i++) {
                Object a =objects.get(i);
                synchronized (a){
                    //分别打印第19次和第20次偏向锁重偏向结果
                    if(i==18||i==19){
                        log.debug("第"+ ( i + 1) + "次偏向结果");
                        log.debug((ClassLayout.parseInstance(a).toPrintable()));
                    }
                }
            }
            try {
                Thread.sleep(10000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t2.start();

        Thread.sleep(3000);
        log.debug("打印list中第11个对象的对象头：");
        log.debug((ClassLayout.parseInstance(objects.get(10)).toPrintable()));
        log.debug("打印list中第26个对象的对象头：");
        log.debug((ClassLayout.parseInstance(objects.get(25)).toPrintable()));
        log.debug("打印list中第41个对象的对象头：");
        log.debug((ClassLayout.parseInstance(objects.get(40)).toPrintable()));
    }
}
