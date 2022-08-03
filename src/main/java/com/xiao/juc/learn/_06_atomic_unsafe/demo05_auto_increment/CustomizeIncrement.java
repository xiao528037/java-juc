package com.xiao.juc.learn._06_atomic_unsafe.demo05_auto_increment;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-15 18:45:09
 * @description
 */
@Slf4j
public class CustomizeIncrement<T> {
    private Supplier<T> increment;

    private Consumer<T> action;

    private ArrayList<Thread> threads=new ArrayList<>();

    private CustomizeIncrement() {
    }

    public CustomizeIncrement(Supplier<T> increment, Consumer<T> action) {
        this.increment = increment;
        this.action = action;
    }

    public void startIncrement() {
        T t = increment.get();
        for (int i = 0; i < 5; i++) {
            threads.add(new Thread(() -> {
                for (int j = 0; j < 500000; j++) {
                    action.accept(t);
                }
            }));
        }
        long begin = System.currentTimeMillis();
        threads.forEach(Thread::start);
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        long end = System.currentTimeMillis() - begin;
        log.debug("{} ----- {}", t, end );
    }

}
