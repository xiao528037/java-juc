package com.xiao.juc.learn._03_thread_synchronize.demo02;

import lombok.extern.slf4j.Slf4j;

/**
 * projectName: java-juc
 * <p>
 * author: aloneMan
 * <p>
 * createTime: 2022-07-11 10:02:07
 * <p>
 * description：
 */
@Slf4j
public class People extends Thread {

    @Override
    public void run() {
        GuardObject<String> guardObject = MailBoxes.createGuardedObject();
        log.debug("<<<< 开始收信  {}", guardObject.getId());
        String message = guardObject.getMessage(5000L);
        log.debug("<<<< 收信内容  {}", message);
    }
}
