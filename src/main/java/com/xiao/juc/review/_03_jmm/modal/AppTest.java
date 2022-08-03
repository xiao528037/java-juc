package com.xiao.juc.review._03_jmm.modal;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-26 16:50:58
 * @description
 */
@Slf4j(topic = "两阶段终止模式测试")
public class AppTest {
    @Test
    public void t1() throws InterruptedException {
        TwoPhaseTermination twoPhaseTermination = new TwoPhaseTermination();
        twoPhaseTermination.startMonitor();
        TimeUnit.SECONDS.sleep(5);
        twoPhaseTermination.stopMonitor();
    }

    @Test
    public void t2() throws InterruptedException {
        BalkingModel balkingModel = new BalkingModel();
        Thread createMonitor = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                balkingModel.start();
            }
        }, "createMonitor");
        createMonitor.start();
        TimeUnit.SECONDS.sleep(3);
        balkingModel.stop();

        createMonitor.join();


    }
}
