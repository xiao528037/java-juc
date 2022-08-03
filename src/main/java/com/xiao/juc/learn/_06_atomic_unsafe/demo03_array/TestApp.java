package com.xiao.juc.learn._06_atomic_unsafe.demo03_array;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicIntegerArray;

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
