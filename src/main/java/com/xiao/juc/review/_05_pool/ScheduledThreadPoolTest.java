package com.xiao.juc.review._05_pool;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-31 13:54:26
 * @description
 */
@Slf4j(topic = "schedulePoll")
public class ScheduledThreadPoolTest {
    @Test
    public void t1() throws InterruptedException {
        ScheduledThreadPoolExecutor poolExecutor = new ScheduledThreadPoolExecutor(2);
        log.debug("()");
        poolExecutor.schedule(() -> {
            log.debug("{} ", "task one");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, 1, TimeUnit.SECONDS);
        poolExecutor.schedule(() -> {
            log.debug("{} ", "task two");
        }, 1, TimeUnit.SECONDS);

        TimeUnit.SECONDS.sleep(10);
    }

    @Test
    public void t2() throws InterruptedException {
        ScheduledThreadPoolExecutor poolExecutor = new ScheduledThreadPoolExecutor(1);
        poolExecutor.scheduleAtFixedRate(() -> {
            log.debug("{} ", new Date());
            try {
//                Thread.sleep(500);
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, 1, 1, TimeUnit.SECONDS);

        TimeUnit.SECONDS.sleep(10);
    }

    @Test
    public void t3() throws InterruptedException {
        ScheduledThreadPoolExecutor poolExecutor = new ScheduledThreadPoolExecutor(1);

        poolExecutor.scheduleWithFixedDelay(() -> {
            log.debug("111");
        }, 1, 1, TimeUnit.SECONDS);

        TimeUnit.SECONDS.sleep(10);
        poolExecutor.shutdown();
    }
}

