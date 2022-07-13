package com.xiao.juc._03_thread_synchronize;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * projectName: java-juc
 * <p>
 * author: aloneMan
 * <p>
 * createTime: 2022-07-10 18:04:57
 * <p>
 * description： wait-notify使用
 */
@Slf4j(topic = "c.ThreadWaitNotify")
public class ThreadWaitNotify {
    private static final Object ROOM = new Object();
    private static Boolean isWater = false;
    private static Boolean isTakeout = false;

    public static void main(String[] args) {
        //等待外卖线程
        new Thread(() -> {
            synchronized (ROOM) {
                log.debug("等待外卖员送外卖...");
                while (!isTakeout) {
                    try {
                        ROOM.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                log.debug("吃到饭了，开始工作...");
            }

        }, "小小").start();
        //等待送水线程
        new Thread(() -> {
            synchronized (ROOM) {
                log.debug("等待小鹏送水...");
                while (!isWater) {
                    try {
                        ROOM.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    log.debug("喝到水了，元气满满...");
                }
            }
        }, "威哥").start();
        //工作线程
        new Thread(() -> {
            synchronized (ROOM) {
                for (int i = 0; i < 5; i++) {
                    log.debug("其他人正在工作...");
                }
            }
        }, "工具人").start();
        //送水线程
        new Thread(() -> {
            synchronized (ROOM) {
                try {
                    ROOM.wait(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.debug("外面送到了,告诉威哥...");
                isTakeout = true;
                ROOM.notifyAll();
            }
        }, "外卖员").start();
        //送外卖线程
        new Thread(() -> {
            synchronized (ROOM) {
                try {
                    ROOM.wait(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.debug("水送到了,告诉小小");
                isWater = true;
                ROOM.notifyAll();
            }
        }, "小鹏").start();
    }
}
