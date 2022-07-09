package com.xiao.juc._01_thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * projectName: java-juc
 * package:com.xiao.juc._01_thread
 * author: aloneMan
 * createTime: 2022-07-09 09:34:56
 * description：
 */
@Slf4j
public class Thread_FutureTask {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
      FutureTask<String> futureTask=  new FutureTask<>(()->{
            return "hello";
        });

        Thread t1 = new Thread(futureTask, "t1");
        t1.start();
        System.out.println(futureTask.get());

    }
}
