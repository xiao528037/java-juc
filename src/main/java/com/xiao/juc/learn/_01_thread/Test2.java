package com.xiao.juc.learn._01_thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * projectName: java-juc
 * package:com.xiao.juc._01_thread
 * author: aloneMan
 * createTime: 2022-07-08 21:33:38
 * description：
 */

@Slf4j
public class Test2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask<>(() -> {
            log.debug("running >>> "+Thread.currentThread().getName());
            Thread.sleep(1000);
            return 521;
        });

        new Thread(task, "我是谁").start();
        //阻塞等待结果返回
        System.out.println(task.get());
    }


}
