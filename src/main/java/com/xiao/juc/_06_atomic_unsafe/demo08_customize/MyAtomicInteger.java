package com.xiao.juc._06_atomic_unsafe.demo08_customize;

import com.xiao.juc._06_atomic_unsafe.interface_packge.UnsafeUtil;
import lombok.ToString;
import sun.misc.Unsafe;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-16 08:27:28
 * @description
 */
@ToString
public class MyAtomicInteger {
    private volatile int value;
    private static Unsafe UNSAFE;

    private static Long valueOffset;


    static {
        UNSAFE = UnsafeUtil.getUnsafe();
        try {
            valueOffset = UNSAFE.objectFieldOffset(MyAtomicInteger.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 增加，并且保证线程安全
     *
     * @param quantity
     *         增加的数量
     */
    public void add(int quantity) {
        int prev;
        do {
            prev = this.value;
        } while (!UNSAFE.compareAndSwapInt(this, valueOffset, prev, this.value + quantity));
    }

    /**
     * 自增
     */
    public void increment() {
        add(1);
    }

    public void reducing(int quantity) {
        int prev;
        do {
            prev = this.value;
        } while (!UNSAFE.compareAndSwapInt(this, valueOffset, prev, prev - quantity));
    }

    public void decrement() {
        reducing(1);
    }

    public int get() {
        return this.value;
    }
}
