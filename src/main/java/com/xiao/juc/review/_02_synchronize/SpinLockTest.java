package com.xiao.juc.review._02_synchronize;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;


/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-23 16:17:20
 * @description
 */
@Slf4j
public class SpinLockTest {
    public static void main(String[] args) throws InterruptedException {
        SpinLock spinLock = new SpinLock();
        Thread t1 = new Thread(() -> {
            spinLock.lock();
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            spinLock.unLock();
        }, "t1");
        Thread t2 = new Thread(() -> {
            spinLock.lock();
            log.debug("{} 执行了", Thread.currentThread().getName());
            spinLock.unLock();
        }, "t2");

        t1.start();
        TimeUnit.SECONDS.sleep(1);
        t2.start();
    }

    @Test
    public void t1() {
        ConcurrentHashMap<Integer, String> str = new ConcurrentHashMap<>();
        str.put(new Integer(1), "111");
        str.put(new Integer(2), "222");
        str.put(new Integer(1), "333");

        String s = str.get(1);
        log.debug(s);


    }

}
