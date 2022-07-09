package com.xiao.juc._01_thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * projectName: java-juc
 * package:com.xiao.juc._01_thread
 * author: aloneMan
 * createTime: 2022-07-09 10:45:44
 * description：park作用类似于sleep，打断park线程，不会清空打断状态（true）,park只能被打断一次，如果再次打断则此方法会失效
 */

@Slf4j
public class Thread_park {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            int i = 0;
            while (i < 10) {
                log.info("park...");
                LockSupport.park();
                log.debug("unpark...");
                log.debug("打断状态 {}  {}", Thread.currentThread().isInterrupted(), i++);
                Thread.interrupted();
            }
        }, "t1");
        t1.start();
        TimeUnit.SECONDS.sleep(1);
        log.info("t1.park...");
        t1.interrupt();
        log.info("interrupted {}", t1.isInterrupted());
    }
}
