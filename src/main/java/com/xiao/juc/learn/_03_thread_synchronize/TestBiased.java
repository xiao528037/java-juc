package com.xiao.juc.learn._03_thread_synchronize;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

/**
 * projectName: java-juc
 * <p>
 * author: aloneMan
 * <p>
 * createTime: 2022-07-10 14:48:26
 * <p>
 * description：查看偏向锁的状态
 */
@Slf4j
public class TestBiased {
    public static void main(String[] args) throws InterruptedException {
        Dog d = new Dog();
        System.out.println(ClassLayout.parseInstance(d).toPrintable());
        synchronized (d) {
            System.out.println(ClassLayout.parseInstance(d).toPrintable());
        }
        for (int i = 0; i <10 ; i++) {
            new Thread(() -> {
                synchronized (d) {
                    System.out.println(ClassLayout.parseInstance(d).toPrintable());
                }
            }, "t1").start();
            new Thread(() -> {
                System.out.println(ClassLayout.parseInstance(d).toPrintable());
            }, "t2").start();
        }

        TimeUnit.SECONDS.sleep(4);
        System.out.println(ClassLayout.parseInstance(new Dog()).toPrintable());
    }
}

class Dog {

}