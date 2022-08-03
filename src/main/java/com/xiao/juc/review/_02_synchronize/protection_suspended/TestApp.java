package com.xiao.juc.review._02_synchronize.protection_suspended;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-24 22:41:40
 * @description
 */
@Slf4j
public class TestApp {
    //
    @Test
    public void t1() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            new PostMan(i % 3, "次数" + i + "你好啊" + i, i * 1000L).start();
        }

        for (int i = 0; i < 3; i++) {
            new People(i, 3000L).start();
        }

        Thread.sleep(20000);
    }

    Integer flag = 1;

    //顺序输出 synchronized+信号量的方式实现
    @Test
    public void t2() throws InterruptedException {

        Thread t1 = new Thread(() -> {
            synchronized (this) {
                while (true) {
                    if (flag != 1) {
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    log.debug("{} ", flag++);
                    this.notify();
                }
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            synchronized (this) {
                while (true) {
                    try {
                        if (flag != 2) {
                            this.wait();
                        }
                        log.debug("{} ", flag--);
                        this.notify();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }, "t2");
        t2.start();
        t1.start();

        t1.join();
    }

    //顺序输出 使用LockSupport实现(伪实现）
    @Test
    public void t3() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (true) {
                LockSupport.park();
                log.debug("{} ", 1);
            }
        }, "t2");
        Thread t2 = new Thread(() -> {
            while (true) {
                log.debug("{} ", 2);
                LockSupport.unpark(t1);
                try {
                    //放弃执行时间片段
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "t1");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }


    @Test
    public void t4() throws InterruptedException {
        AwaitSignal awaitSignal = new AwaitSignal(5);
        Condition cOne = awaitSignal.newCondition();
        Condition cTwo = awaitSignal.newCondition();
        Condition cThree = awaitSignal.newCondition();
        new Thread(() -> {
            awaitSignal.print("a", cOne, cTwo);
        }).start();
        new Thread(() -> {
            awaitSignal.print("b", cTwo, cThree);
        }).start();
        new Thread(() -> {
            awaitSignal.print("c", cThree, cOne);
        }).start();


        TimeUnit.SECONDS.sleep(1);
        awaitSignal.lock();
        try {
            cOne.signal();
        } finally {
            awaitSignal.unlock();
        }

        TimeUnit.SECONDS.sleep(10);
    }
}

@Slf4j(topic = "顺序输出")
class AwaitSignal extends ReentrantLock {
    private int loopNumber;

    public AwaitSignal(int loopNumber) {
        this.loopNumber = loopNumber;
    }

    public void print(String printContent, Condition condition, Condition next) {
        for (int i = 0; i < loopNumber; i++) {
            lock();
            try {
                condition.await();
                log.debug("{} ", printContent);
                next.signal();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                unlock();
            }
        }
    }
}
