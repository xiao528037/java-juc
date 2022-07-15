package com.xiao.juc._06_atomic_unsafe.demo05_auto_increment;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-15 18:53:39
 * @description
 */
public class TestApp {
    @Test
    public void test1(){
        new CustomizeIncrement<>(()-> new AtomicLong(0),atomicLong -> atomicLong.getAndIncrement()).startIncrement();

        new CustomizeIncrement<>(()->new LongAdder(),longAdder -> longAdder.increment()).startIncrement();
    }
}
