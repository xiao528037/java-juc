package com.xiao.juc.learn._04_reentrant_lock.demo01;

/**
 * projectName: java-juc
 * <p>
 * author: aloneMan
 * <p>
 * createTime: 2022-07-12 19:41:53
 * <p>
 * description：线程的哲学家问题
 */
public class TestApp {
    public static void main(String[] args) {
        Chopstick lock1 = new Chopstick(1);
        Chopstick lock2 = new Chopstick(2);
        Chopstick lock3 = new Chopstick(3);
        Chopstick lock4 = new Chopstick(4);
        Chopstick lock5 = new Chopstick(5);

        new Philosopher("李狗嗨", lock1, lock2).start();
        new Philosopher("梵高", lock2, lock3).start();
        new Philosopher("墨子", lock3, lock4).start();
        new Philosopher("祖冲之", lock4, lock5).start();
        new Philosopher("孔子", lock5, lock1).start();

    }
}
