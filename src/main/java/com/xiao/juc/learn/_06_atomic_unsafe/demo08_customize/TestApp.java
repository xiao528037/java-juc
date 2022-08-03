package com.xiao.juc.learn._06_atomic_unsafe.demo08_customize;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-16 08:42:44
 * @description
 */
public class TestApp {

    @Test
    public void test01() throws InterruptedException {
        ArrayList<Thread> decrementThreads = new ArrayList<>();
        MyAtomicInteger myAtomicInteger = new MyAtomicInteger();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 500000; i++) {
                myAtomicInteger.increment();
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 500000; i++) {
                myAtomicInteger.increment();
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(myAtomicInteger.get());
        for (int i = 0; i < 100; i++) {
            decrementThreads.add(new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    myAtomicInteger.decrement();
                }
            }));
        }

        decrementThreads.forEach(Thread::start);



        decrementThreads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println(myAtomicInteger.get());

    }
}
