package com.xiao.juc._06_atomic_unsafe.demo01_atomicinteger;

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
    public void Test01() {
        TestAtomicInteger testAtomicInteger = new TestAtomicInteger(new AtomicInteger(10000));
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            final int finalI = i + 1;
            Thread thread = new Thread(() -> {
                for (int j = 0; j < finalI; j++) {
                    testAtomicInteger.withdraw(10);
                }

            });
            threads.add(thread);
        }
        threads.forEach(item -> item.start());
        AtomicInteger money = testAtomicInteger.getMoney();
        log.info("{}", money);
    }

    @Test
    public void Test02() {
        TestAtomicInteger testAtomicInteger = new TestAtomicInteger(new AtomicInteger(10000));
        new Thread(() -> {
            testAtomicInteger.withdraw(100);
        }).start();
    }
}
