package com.xiao.juc.review._06_aqs.exchanger;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-08-03 15:53:43
 * @description
 */

@Slf4j(topic = "exchanger-t")
public class ExchangerTest {

    @Test
    public void t1() throws InterruptedException {
        Exchanger<String> oe = new Exchanger<>();
        new ThreadA(oe).start();
        new ThreadB(oe).start();
        new ThreadC(oe).start();
        TimeUnit.SECONDS.sleep(10);
    }
}
