package com.xiao.juc.review._06_aqs.PhilosopherDemo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-08-02 00:20:08
 * @description
 */
@Slf4j
public class TestApp {

    private ReentrantLock myLock = new ReentrantLock();
    private Condition con1 = myLock.newCondition();
    private Condition con2 = myLock.newCondition();
    private Condition con3 = myLock.newCondition();
    private Condition con4 = myLock.newCondition();
    private Condition con5 = myLock.newCondition();

    @Test
    public void t1() throws InterruptedException {
        new Philosopher("1", myLock, con1, con2).start();
        new Philosopher("2", myLock, con2, con3).start();
        new Philosopher("3", myLock, con3, con4).start();
        new Philosopher("4", myLock, con4, con5).start();
        new Philosopher("5", myLock, con5, con1).start();
        TimeUnit.SECONDS.sleep(2);
        myLock.lock();
        try {
            con1.signal();
        } finally {
            myLock.unlock();
        }



        TimeUnit.SECONDS.sleep(10);
    }
}
