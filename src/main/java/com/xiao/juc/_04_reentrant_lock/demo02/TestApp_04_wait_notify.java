package com.xiao.juc._04_reentrant_lock.demo02;

import lombok.extern.slf4j.Slf4j;

/**
 * projectName: java-juc
 * <p>
 * author: aloneMan
 * <p>
 * createTime: 2022-07-13 10:59:49
 * <p>
 * description：控制输出顺序
 */
public class TestApp_04_wait_notify {

    public static void main(String[] args) {
        WaitNotify waitNotify = new WaitNotify(1, 5);
        new Thread(() -> {
            waitNotify.print("A", 1);
        }, "print-A").start();
        new Thread(() -> {
            waitNotify.print("B", 2);
        }, "print-B").start();
        new Thread(() -> {
            waitNotify.print("C", 3);
        }, "print-C").start();

    }
}

@Slf4j
class WaitNotify {
    /**
     * @param str
     *         打印的信息
     * @param waitFlag
     *         等待的标志
     */
    public void print(String str, int waitFlag) {
        for (int i = 0; i < loopNumber; i++) {
            synchronized (this) {
                while (flag != waitFlag) {
                    //当需要执行的线程不等于对象的标记时,当前线程进入等待状态
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (flag == 1) {
                    log.debug("----------");
                }
                log.debug("打印的内容 {} ", str);
                //计算下一个要执行的flag
                flag = (waitFlag % 3) + 1;
                //唤醒所有等待线程
                this.notifyAll();
            }
        }
    }

    private Integer flag;
    private Integer loopNumber;

    /**
     * @param flag
     *         当前的标记，用来判断是否执行打印，如果当前标记不等于传入的标记，则执行等待
     * @param loopNumber
     *         打印的次数
     * @返回参数: 无
     * @创建人：肖杰斌
     * @创建时间：2022/7/13
     * @描述：顺序打印信息
     */
    public WaitNotify(Integer flag, Integer loopNumber) {
        this.flag = flag;
        this.loopNumber = loopNumber;
    }
}