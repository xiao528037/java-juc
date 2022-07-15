package com.xiao.juc._06_atomic_unsafe.demo03_array;

import ch.qos.logback.core.net.server.ConcurrentServerRunner;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-15 17:27:53
 * @description
 */
public class TestApp {
    @Test
    public void ordinaryArrayTest01() {
        new IncrementIntegerArray<>(
                () -> new int[10],
                array -> array.length,
                (array, index) -> array[index]++,
                (array) -> System.out.println(Arrays.toString(array))).start();
    }



    @Test
    public void atomicArrayTest02() {
        new IncrementIntegerArray<>(() -> new AtomicIntegerArray(10),
                array -> array.length(),
                (array, index) -> array.getAndIncrement(index),
                array -> System.out.println(array)).start();
    }


}
