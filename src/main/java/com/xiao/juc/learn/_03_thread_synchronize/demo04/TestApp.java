package com.xiao.juc.learn._03_thread_synchronize.demo04;

import com.xiao.juc.learn._03_thread_synchronize.demo05.BigRoom;

/**
 * projectName: java-juc
 * <p>
 * author: aloneMan
 * <p>
 * createTime: 2022-07-12 16:41:23
 * <p>
 * description：多把锁，每一把锁，锁定的区域不去调用公共数据以及业务关系
 * <P>
 *              好处：增加并发度
 * <p>
 *              坏处：如果一个线程需要同事获得多把锁，容易死锁
 */
public class TestApp {
    public static void main(String[] args) {
        BigRoom bigRoom = new BigRoom();
        new Thread(()->{
            try {
                bigRoom.studyRoom();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"大儿子" ).start();

        new Thread(()->{
            try {
                bigRoom.bedRoom();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"小儿子" ).start();
    }
}
