package com.xiao.juc.review._02_synchronize.protection_suspended;

import lombok.extern.slf4j.Slf4j;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-24 22:21:44
 * @description 邮差
 */

@Slf4j(topic = "邮差")
public class PostMan extends Thread {

    private Integer giveMailId;//送给那个用户的邮件

    private String mailContent;//送给用户的邮件内容

    private Long howLong;//预计配送时间

    public PostMan(Integer giveMailId, String mailContent, Long howLong) {
        this.giveMailId = giveMailId;
        this.mailContent = mailContent;
        this.howLong = howLong;
    }

    @Override
    public void run() {

//        log.debug("{} 开始送信", Thread.currentThread().getName());
        try {
            Thread.sleep(howLong);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        BoxMail.deliverMail(giveMailId, mailContent);
//        log.debug("{} 送信完成", Thread.currentThread().getName());
    }
}
