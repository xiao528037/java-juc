package com.xiao.juc.review._02_synchronize.asynchronous_model.version01;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-25 12:19:35
 * @description
 */
public class TestApp {

    @Test
    public void t1() throws InterruptedException {
        ShareData shareData = new ShareData();
        new Thread(()->{
            while (true){
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                shareData.increment();
            }
        }).start();
        new Thread(()->{
            while (true){
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                shareData.decrement();
            }
        }).start();
        TimeUnit.SECONDS.sleep(5);
    }
}
