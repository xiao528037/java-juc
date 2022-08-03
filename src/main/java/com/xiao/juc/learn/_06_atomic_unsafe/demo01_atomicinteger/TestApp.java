package com.xiao.juc.learn._06_atomic_unsafe.demo01_atomicinteger;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-15 10:35:13
 * @description
 */
@Slf4j

public class TestApp {
    @Test
    public void test01() {
        TestAtomicInteger testAtomicInteger = new TestAtomicInteger(new AtomicInteger(10000));
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            final int finalI = i + 1;
            Thread thread = new Thread(() -> {
                for (int j = 0; j < finalI; j++) {
                    testAtomicInteger.withdraw(new AtomicInteger(10));
                }

            });
            threads.add(thread);
        }
        threads.forEach(item -> item.start());
        AtomicInteger money = testAtomicInteger.getMoney();
        log.info("{}", money);
    }

    @Test
    public void test02() {
        TestAtomicInteger testAtomicInteger = new TestAtomicInteger(new AtomicInteger(10000));
        new Thread(() -> {
            testAtomicInteger.withdraw(new AtomicInteger(100));
        }).start();
    }

    @Test
    public void test03() {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        //先自增在获取
        log.info("{}", atomicInteger.incrementAndGet());
        //先获取在自增
        log.info("{}", atomicInteger.getAndIncrement());

        //先自减在获取
        log.info("{}", atomicInteger.decrementAndGet());
        //先获取在自减
        log.info("{}", atomicInteger.getAndDecrement());
        log.info("{}", atomicInteger.get());

        //先获取在自加上传进去的参数
        log.info("{}", atomicInteger.getAndAdd(5));
        //先加上传进去的参数在获取
        log.info("{}", atomicInteger.addAndGet(5));


        log.info("{}", atomicInteger.getAndUpdate(i -> i * 100000));
        log.info("{}", atomicInteger.updateAndGet(x -> x / 100000));
        log.info("{}", atomicInteger.get());

        AtomicInteger atomicInteger1 = new AtomicInteger(0);
        int z = 100000;
        while (z > 0) {
            int i = myAdd(x -> x + 1, atomicInteger1);
            log.info("{}", i);
            z--;
        }

    }


    public int myAdd(CustomizeFunction customizeFunction, AtomicInteger i) {
        int add;
        do {
            add = customizeFunction.add(i.get());
        } while (!i.compareAndSet(i.get(), add));
        return add;
    }

    @Test
    public void test04() {
        int i = 0;

        log.info("{}", i++);
        log.info("{}", ++i);
        log.info("{}", i--);
        log.info("{}", --i);
    }
}
