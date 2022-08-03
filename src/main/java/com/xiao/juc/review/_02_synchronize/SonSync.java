package com.xiao.juc.review._02_synchronize;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-22 17:29:20
 * @description
 */
public class SonSync extends ParentSync {

    private static int sonCount = 0;


    public static synchronized void sonIncrement() {
        sonCount++;
    }

    public static synchronized void sonDecrement() {
        sonCount--;
    }

    @Override
    public synchronized void increment() {
        super.increment();
    }

    @Override
    public synchronized void decrement() {
        super.decrement();
    }

    public static int getSonCount() {
        return sonCount;
    }
}
