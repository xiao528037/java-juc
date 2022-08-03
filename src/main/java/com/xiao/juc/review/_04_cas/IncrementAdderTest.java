package com.xiao.juc.review._04_cas;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-27 12:10:46
 * @description 累加器测试
 */
@Slf4j(topic = "累加器")
public class IncrementAdderTest {

    @Test
    public void longAdderTest() throws InterruptedException {
        LongAdder longAdder = new LongAdder();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                longAdder.increment();
            }
        }, "t1");
        t1.start();
        t1.join();
        log.debug("{} ", longAdder);
        LongAccumulator longAccumulator = new LongAccumulator((a, b) -> {
            a = a + b;
            return a;
        }, 0);
        for (int i = 0; i < 100; i++) {
            longAccumulator.accumulate(2);
        }
        log.debug("{} ",longAccumulator.get());
    }
}
