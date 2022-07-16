package com.xiao.juc._06_atomic_unsafe.demo04_field;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-15 18:30:49
 * @description
 */
public class Person {
    volatile String name;


    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }
}
