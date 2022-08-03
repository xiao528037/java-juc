package com.xiao.juc.review._02_synchronize;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.layouters.CurrentLayouter;
import org.openjdk.jol.layouters.HotSpotLayouter;
import org.openjdk.jol.layouters.Layouter;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-23 10:32:47
 * @description 模拟批量批量偏向，批量撤销
 */
@Slf4j(topic = "x.lock.bias")
public class BiaseLock {

    public static void main(String[] args) throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        //将创建的线程存储
        ArrayList<Object> locks = new ArrayList<>();
        new Thread(() -> {
            for (int i = 1; i <= 30; i++) {
                int j = i;
                //每个线程创建一把锁
                Object o = new Object();
                locks.add(o);
                synchronized (o) {
                    if (j == 1 || j == 20) {
                        String s = ClassLayout.parseInstance(o).toPrintable();
                        log.debug("偏向锁 {} \n {}", j, s);
                    }
                }
            }
            synchronized (locks) {
                locks.notify();
            }
        }, "t1").start();
        new Thread(() -> {
            synchronized (locks) {
                try {
                    locks.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            for (int i = 0; i < 30; i++) {
                Object o = locks.get(i);
                String s = ClassLayout.parseInstance(o).toPrintable();
                if (i == 17 || i == 18 || i == 19) {
                    log.debug("+++++++++++++++++++++++");
                    log.debug("未重新偏向 {} \n {}", i + 1, s);
                    synchronized (o) {
                        String s2 = ClassLayout.parseInstance(o).toPrintable();
                        log.debug("开始重新偏向 {} \n {} ", i + 1, s2);
                    }
                    String s2 = ClassLayout.parseInstance(o).toPrintable();
                    log.debug("偏向结束 {} \n {} ", i + 1, s2);
                    log.debug("-----------------------");
                }
                synchronized (o) {
                }

            }
        }, "t2").start();

        TimeUnit.SECONDS.sleep(3);
        Object o = locks.get(17);
        String s = ClassLayout.parseInstance(o).toPrintable();
        log.debug("批量重新偏向后  \n {}", s);
    }
}

class Dog {

}
