package com.xiao.juc.review._02_synchronize.park_unpark;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-24 20:59:56
 * @description
 */
@Slf4j(topic = "c.park")
public class ParkUnParkTest {

    //先unPark()
    @Test
    public void t1() {
        Thread th = new Thread(() -> {
            log.debug("{} 进行了park", Thread.currentThread().getName());

            LockSupport.park();
            log.debug("{} 运行结束", Thread.currentThread().getName());
        });
        th.start();
        LockSupport.unpark(th);

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //先park
    @Test
    public void t2() {
        Thread thread = new Thread(() -> {
            log.debug("{} 进行了park", Thread.currentThread().getName());
            LockSupport.park();
            log.debug("{} 运行结束", Thread.currentThread().getName());
        });
        thread.start();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        LockSupport.unpark(thread);

    }
}


