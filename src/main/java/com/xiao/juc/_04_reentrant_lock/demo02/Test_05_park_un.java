package com.xiao.juc._04_reentrant_lock.demo02;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * projectName: java-juc
 * <p>
 * author: aloneMan
 * <p>
 * createTime: 2022-07-13 12:01:22
 * <p>
 * description：
 */
@Slf4j
public class Test_05_park_un {
    private static Thread t1;
    private static Thread t2;
    private static Thread t3;
    private static Thread t4;

    public static void main(String[] args) {
        WaitNotify02 waitNotify02 = new WaitNotify02(1, 5, 4);
        t1 = new Thread(() -> {
            waitNotify02.printParkUn("A", 1, t2);
        }, "t1");
        t2 = new Thread(() -> {
            waitNotify02.printParkUn("B", 2, t3);
        }, "t2");
        t3 = new Thread(() -> {
            waitNotify02.printParkUn("C", 3, t4);
        }, "t3");
        t4 = new Thread(() -> {
            waitNotify02.printParkUn("D", 4, t1);
        }, "t4");
        t1.start();
        t2.start();
        t3.start();
        t4.start();

    }
}

@Slf4j
class WaitNotify02 {

    /**
     * @param content
     *         打印内容
     * @param current
     *         执行的标记
     * @param thread
     *         需要唤醒的线程
     */
    public void printParkUn(String content, Integer current, Thread thread) {
        for (int i = 0; i < loopNumber; i++) {
            while (!current.equals(flag)) {
                //进入等待，
                LockSupport.park();
            }
            if (flag == 1) {
                log.debug("----------");
            }
            log.debug("{}", content);
            flag = (current % printSize) + 1;
            LockSupport.unpark(thread);
        }
    }

    private Integer flag;
    private Integer loopNumber;
    private Integer printSize;

    /**
     * 构造方法
     *
     * @param flag
     *         初始化标记
     * @param loopNumber
     *         打印的次数
     * @param printSize
     *         顺序打印的长度
     */
    public WaitNotify02(Integer flag, Integer loopNumber, Integer printSize) {
        this.flag = flag;
        this.loopNumber = loopNumber;
        this.printSize = printSize;
    }
}
