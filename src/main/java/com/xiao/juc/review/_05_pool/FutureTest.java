package com.xiao.juc.review._05_pool;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-31 13:11:58
 * @description
 */
@Slf4j(topic = "future")
public class FutureTest {
    @Test
    public void t1() throws ExecutionException, InterruptedException {
        FutureTask<String> stringFutureTask = new FutureTask<>(() -> {
            int i=1/0;
            return "hello future";
        });
        Thread thread = new Thread(stringFutureTask, "executorFuture");
        thread.start();
        log.debug("{} ", stringFutureTask.get());
//        log.debug("{}",stringFutureTas);
    }
}
