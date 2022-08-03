package com.xiao.juc.review._05_pool;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-31 13:48:58
 * @description
 */

@Slf4j(topic = "timer")
public class TimerTest {
    @Test
    public void t1() throws InterruptedException {
        TimerTask task_one = new TimerTask() {
            @Override
            public void run() {
                log.debug("{} ", "task one");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        TimerTask task_two = new TimerTask() {
            @Override
            public void run() {
                log.debug("{} ", "task two");
            }
        };
        Timer timer = new Timer();
        timer.schedule(task_one, 1000);
        timer.schedule(task_two, 1000);
        TimeUnit.SECONDS.sleep(10);
    }
}
