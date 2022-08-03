package com.xiao.juc.review._01_thread.TwoPahseTermination;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-22 14:59:38
 * @description
 */
@Slf4j(topic = "c.x.tpt")
public class TwoPhaseTermination {
    Thread monitor;

    public void start() {
        monitor = new Thread(() -> {
            Thread thread = Thread.currentThread();
            while (true) {
                if (thread.isInterrupted()) {
                    log.debug("执行结束...");
                    break;
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                    log.debug("执行监听...");
                } catch (InterruptedException e) {
                    //如果在睡眠时打断不会将打断状态设置成true，重置他的打断状态
                    thread.interrupt();
                }
            }
        }, "monitor");
        monitor.start();
    }

    public void stop() {
        monitor.interrupt();
    }

    @Test
    public void TwoPhaseTerminationTest() throws InterruptedException {
        TwoPhaseTermination twoPhaseTermination = new TwoPhaseTermination();
        twoPhaseTermination.start();
        TimeUnit.SECONDS.sleep(10);
        twoPhaseTermination.stop();
        twoPhaseTermination.monitor.join();
    }
}
