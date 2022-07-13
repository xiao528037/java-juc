package com.xiao.juc._04_reentrant_lock.demo02;

import jdk.nashorn.internal.ir.Block;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * projectName: java-juc
 * <p>
 * author: aloneMan
 * <p>
 * createTime: 2022-07-13 09:45:10
 * <p>
 * description：使用park和unpark的方式进行顺序打印
 */
@Slf4j
public class TestApp_02 {

    private static Object lock = new Object();
    private static boolean flag = false;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {


            while (!flag) {
                LockSupport.park();
            }

            log.debug("1111");


        });
        Thread t2 = new Thread(() -> {
            log.debug("2222");
            flag = true;
            LockSupport.unpark(t1);

        });
        t1.start();
        t2.start();

    }
}
