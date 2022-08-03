package com.xiao.juc.learn._04_reentrant_lock.demo01;

import java.util.concurrent.locks.ReentrantLock;

/**
 * projectName: java-juc
 * <p>
 * author: aloneMan
 * <p>
 * createTime: 2022-07-12 19:36:21
 * <p>
 * description：筷子
 */
public class Chopstick extends ReentrantLock {
    private Integer name;

    public Chopstick(Integer name) {
        this.name = name;
    }
}
