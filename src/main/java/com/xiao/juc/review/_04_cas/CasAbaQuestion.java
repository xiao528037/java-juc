package com.xiao.juc.review._04_cas;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-26 17:17:10
 * @description
 */
@Slf4j(topic = "cas")
public class CasAbaQuestion {
    private AtomicStampedReference<Integer> atomicInteger = new AtomicStampedReference<>(1, 0);

    @Test
    public void t1() throws InterruptedException {
        new Thread(() -> {
            while(true){
                Integer reference = atomicInteger.getReference();
                int stamp = atomicInteger.getStamp();
                if (atomicInteger.compareAndSet(reference, 2, stamp, stamp + 1)){
                    log.debug("{} ",atomicInteger.getReference());
                }
            }

        }, "t1").start();
        new Thread(() -> {
            while(true){
                Integer reference = atomicInteger.getReference();
                int stamp = atomicInteger.getStamp();
                if (atomicInteger.compareAndSet(reference, 1, stamp, stamp + 1)){
                    log.debug("{} ",atomicInteger.getReference());
                }
            }
        }, "t2").start();
        TimeUnit.SECONDS.sleep(2);
    }

}
