package com.xiao.juc.learn._08_concurrent_packge.demo05;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-21 14:40:33
 * @description
 */
public class MOCAPlayer {
    static ExecutorService pool = Executors.newFixedThreadPool(10);
    static CountDownLatch cdl = new CountDownLatch(10);
    static Random random = new Random();

    @Test
    public static void main(String[] args) throws InterruptedException {
        String[] gamePlayer = new String[10];
        for (int j = 0; j < 10; j++) {
            int z = j;
            pool.submit(() -> {
                for (int i = 0; i <= 100; i++) {
                    try {
                        Thread.sleep(random.nextInt(100));
                        gamePlayer[z] = i + "%";
                        if (i == 100) {
                            cdl.countDown();
                            System.out.println("----");
                        }
                        System.out.print("\r" + Arrays.toString(gamePlayer));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }, "submitThread");
        }
        cdl.await();


//        Thread.sleep(100000);
    }
}
