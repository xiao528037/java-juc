package com.xiao.juc.review._04_cas.threadlocal;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-28 10:20:46
 * @description
 */
@Slf4j(topic = "ThreadLocal-Test")
public class ThreadLocalTest {
    private ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public void setContent(String content) {
        threadLocal.set(content);
    }

    public String getContent() {
        return threadLocal.get();
    }

    public static void main(String[] args) {
        ThreadLocalTest threadLocalTest = new ThreadLocalTest();
        for (int i = 0; i < 500; i++) {
            int j = i;
            new Thread(() -> {
                threadLocalTest.setContent(Thread.currentThread().getName() + "-" + j);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.debug("{} ", threadLocalTest.getContent());
            }, i + "").start();
        }
    }


    @Test
    public void t1() {
        int[] array = new int[]{186,419,83,408};
        int i = coinChange(array, 6249);
        log.debug("{} ", i);
    }

    public static int coinChange(int[] coins, int amount) {
        int remaining;
        int count = 0;
        Arrays.sort(coins);
        for (int i = coins.length - 1; i >= 0; i--) {
            if ((remaining = amount % coins[i]) != 0) {
                if (remaining == amount || (i == 0 && remaining != 0)) {
                    if (i != 0) continue;
                    return -1;
                }
                count += amount / coins[i];
                amount = remaining;
                continue;
            } else {
                count += amount / coins[i];
                return count;
            }
        }
        return count;
    }
}
