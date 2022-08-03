package com.xiao.juc.review._04_cas;

import sun.misc.Unsafe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-26 22:02:44
 * @description 通过反射获取到unsafe对象
 */
public class UnsafeUtil {
    private static volatile Unsafe unsafe = null;

    private static ReentrantLock lock = new ReentrantLock();

    public static Unsafe getUnsafe() {
        if (unsafe == null) {
            lock.lock();
            try {
                if (unsafe == null) {
                    Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
                    theUnsafe.setAccessible(true);
                    unsafe = (Unsafe) theUnsafe.get(null);
                }
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }
        return unsafe;
    }
}
