package com.xiao.juc.review._02_synchronize.protection_suspended;

import lombok.extern.slf4j.Slf4j;

import static com.xiao.juc.review._02_synchronize.protection_suspended.BoxMail.takeMail;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-24 22:22:50
 * @description 收信
 */
@Slf4j(topic = "收信人")
public class People extends Thread {

    private Integer id;

    private Long milliSecond;

    public People(Integer id, Long milliSecond) {
        this.id = id;
        this.milliSecond = milliSecond;
    }

    @Override
    public void run() {
        String content = takeMail(id, milliSecond);
        log.debug("用户 {} 收到邮件,内容为 {}", id, content);
    }
}
