package com.xiao.juc._08_concurrent_packge.demo04;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-18 12:07:50
 * @description
 */
@Slf4j(topic = "x.mylock.test")
public class TestApp {
    @Test
    public void myLockTet() throws InterruptedException {

        MyLock myLock = new MyLock();
        for (int i = 0; i <= 1000; i++) {
            new Thread(() -> {
                myLock.lock();
                try {
                    log.debug("{} 开始执行", Thread.currentThread().getName());

                    log.debug("{} 执行结束", Thread.currentThread().getName());
                } finally {
                    myLock.unlock();
                    log.debug("解锁成功...");
                }
            }, "t--" + i).start();
        }

        Thread.sleep(2000);
        log.debug("-0-0-0-0-0-0-0-0-");
        Thread t1 = new Thread(() -> {
            myLock.lock();
            try {
                log.debug("{} 开始执行", Thread.currentThread().getName());
                Thread.sleep(2000);
                log.debug("{} 执行结束", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                myLock.unlock();
                log.debug("解锁成功...");
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            myLock.lock();
            try {
                log.debug("{} 开始执行", Thread.currentThread().getName());
                Thread.sleep(1000);
                log.debug("{} 执行结束", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                myLock.unlock();
                log.debug("解锁成功...");
            }
        }, "t2");
        t1.start();
        t2.start();

        t1.join();
        t2.join();

    }

    @Test
    public void t2() {
        ReentrantLock reentrantLock = new ReentrantLock();
        new Thread(() -> {
            reentrantLock.lock();
            try {
                log.debug("{} 线程执行...", Thread.currentThread().getName());
                Thread.sleep(5000);
                log.debug("{} 执行结束...", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                reentrantLock.unlock();
            }
        }, "t1").start();
        new Thread(() -> {
            reentrantLock.lock();
            try {
                log.debug("{} 线程执行...", Thread.currentThread().getName());
                Thread.sleep(1000);
                log.debug("{} 执行结束...", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                reentrantLock.unlock();
            }
        }, "t2").start();

    }

    @Test
    public void t3() {
        String string = "1";
        Integer integer = 1;

        Person person1 = new Person(string, integer);
        Person person2 = new Person(string, integer);
        System.out.println(person1.equals(person2));

        System.out.println((1 << 16) );
        System.out.println(Integer.MAX_VALUE);

        int i = 1;
        int j = 2;
        int z = 3;
        System.out.println(i = j = z);
    }

    @Test
    public void t4() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Thread t1 = new Thread(() -> {


            lock.lock();
            try {

                log.debug("11111");
                Thread.sleep(2000000);
                log.debug("t1t1");


            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }

        }, "t1");
        Thread t2 = new Thread(() -> {
            lock.lock();
            try {
                log.debug("t2");
            } finally {
                lock.unlock();
            }
        }, "t2");
        t1.start();
        Thread.sleep(1000);
        t2.start();

        System.out.println("22222");
        t1.join();
    }

    @Test
    public void t5() {
        int nums[] = new int[]{8, 3, 5, 7};
        int t = t(nums);
        log.debug("{}", t);
    }

    @Test
    public void t6() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock(true);
        Condition condition = lock.newCondition();

        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                condition.await();
                log.debug("{} 执行了...", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }

        }, "t1");
        Thread t2 = new Thread(() -> {
            lock.lock();
            try {
                log.debug("{} 执行了...", Thread.currentThread().getName());
            } finally {
                lock.unlock();
            }
        }, "t2");
        t1.start();
        Thread.sleep(1000);
        t2.start();
        t1.join();
    }

    public int t(int[] nums) {
        int max = nums[0], secondMax = 0, min = nums[0], secondMin = 0;
        for (int i = 1; i < nums.length; i++) {
            if (secondMax == 0) {
                if (nums[i] > max) {
                    secondMax = max;
                    max = nums[i];
                } else {
                    secondMax = nums[i];
                }
            } else {
                if (nums[i] > secondMax) {
                    if (nums[i] > max) {
                        secondMax = max;
                        max = nums[i];
                    } else {
                        secondMax = nums[i];
                    }
                }
            }

            //获取最小两个值
            if (secondMin == 0) {
                if (nums[i] < min) {
                    secondMin = min;
                    min = nums[i];
                } else {
                    secondMin = nums[i];
                }
            } else {
                if (nums[i] < secondMin) {
                    if (nums[i] < min) {
                        secondMin = min;
                        min = nums[i];
                    } else {
                        secondMin = nums[i];
                    }
                }
            }
        }
        return (max * secondMax) - (min * secondMin);
    }

    @Test
    public void t7() {
        System.out.println(null != null);
    }


    @Test
    public void t8() throws InterruptedException {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
        Thread t1 = new Thread(() -> {
            writeLock.lock();
            try {
                Thread.sleep(3000);
                log.debug("{} 执行了....", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                writeLock.unlock();
            }
        }, "t1");

        new Thread(() -> {
            writeLock.lock();
            try {
                Thread.sleep(2000);
                log.debug("{} 执行了....", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                writeLock.unlock();
            }
        }, "t2").start();

        t1.start();
        t1.join();

    }

    @Test
    public void t9() {
//        System.out.println(Integer.MAX_VALUE);

        System.out.println((1<<16)-1);
        System.out.println(0&0);
    }
}
