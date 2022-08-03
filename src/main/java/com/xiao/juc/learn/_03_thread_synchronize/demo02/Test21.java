package com.xiao.juc.learn._03_thread_synchronize.demo02;

import static java.lang.Thread.sleep;

/**
 * projectName: java-juc
 * <p>
 * author: aloneMan
 * <p>
 * createTime: 2022-07-11 14:01:37
 * <p>
 * description：
 */
public class Test21 {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            new People().start();
        }
        Thread.sleep(1000);
        for (Integer guardedById : MailBoxes.getGuardedByIds()) {
            new Postman<String>(guardedById, "这是 " + guardedById + "的邮件").start();
        }
    }
}
