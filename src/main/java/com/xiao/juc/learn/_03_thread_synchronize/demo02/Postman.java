package com.xiao.juc.learn._03_thread_synchronize.demo02;

import lombok.extern.slf4j.Slf4j;

/**
 * projectName: java-juc
 * <p>
 * author: aloneMan
 * <p>
 * createTime: 2022-07-11 08:45:23
 * <p>
 * description：邮递员
 */
@Slf4j
public class Postman<T> extends Thread {
    private int id;

    private T message;

    public Postman(int id, T message) {
        this.id = id;
        this.message = message;
    }

    @Override
    public void run() {
        log.debug(">>>> 开始送信");
        GuardObject<T> guardObject = MailBoxes.getGuardedById(id);
        guardObject.complete(message);
        log.debug(">>>> 送信完成");
    }
}
