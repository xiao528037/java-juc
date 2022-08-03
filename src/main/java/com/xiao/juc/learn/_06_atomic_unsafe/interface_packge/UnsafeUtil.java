package com.xiao.juc.learn._06_atomic_unsafe.interface_packge;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-16 08:25:24
 * @description
 */
public class UnsafeUtil {
    private final static Unsafe UNSAFE;

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            UNSAFE = (Unsafe) theUnsafe.get(null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Unsafe getUnsafe() {
        return UNSAFE;
    }
}
