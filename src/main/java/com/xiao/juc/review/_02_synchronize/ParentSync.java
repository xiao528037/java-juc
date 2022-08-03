package com.xiao.juc.review._02_synchronize;

import lombok.extern.slf4j.Slf4j;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-22 17:24:52
 * @description
 */
@Slf4j
public class ParentSync {
    private Integer count = 0;

    public synchronized void increment() {
            count++;
    }

    public synchronized void decrement() {
            count--;
    }

    public synchronized Integer getCount() {
        return count;
    }
}
