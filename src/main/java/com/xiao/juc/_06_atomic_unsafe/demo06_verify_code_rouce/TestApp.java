package com.xiao.juc._06_atomic_unsafe.demo06_verify_code_rouce;

import org.junit.jupiter.api.Test;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-15 21:18:01
 * @description
 */
public class TestApp {


    @Test
    public void t1() {
        Integer a, base = 1;
        increment(a = new Integer(2), base + a);
        System.out.println(6&1);
        int t=0x9e3779b9;
        System.out.println(t);
        System.out.println(Runtime.getRuntime().availableProcessors());
        System.out.println(123<<1);
    }

    public void increment(int a, int b) {
        System.out.println(a + b);
    }
}
