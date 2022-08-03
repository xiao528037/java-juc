package com.xiao.juc.learn._03_thread_synchronize.demo05;

import org.junit.jupiter.api.Test;

/**
 * projectName: java-juc
 * <p>
 * author: aloneMan
 * <p>
 * createTime: 2022-07-12 16:53:03
 * <p>
 * description：
 */
public class DeadLock {

    @Test
    public void testDeadLock() {
        BigRoom bigRoom = new BigRoom();
        new Thread(() -> {
            try {
                bigRoom.studyRoom();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "学习线程").start();
        new Thread(() -> {
            try {
                bigRoom.bedRoom();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "睡觉线程").start();

    }
}




