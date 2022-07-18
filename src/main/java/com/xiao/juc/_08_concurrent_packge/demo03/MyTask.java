package com.xiao.juc._08_concurrent_packge.demo03;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.RecursiveTask;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-17 21:09:07
 * @description
 */
@Slf4j(topic = "c.MyTask")
public class MyTask extends RecursiveTask<Integer> {
    private Integer number;

    public MyTask(Integer number) {
        this.number = number;
    }

    @Override
    protected Integer compute() {
        if (number == 1) {
//            log.debug("开始返回 {}", number);
            return number;
        }

        MyTask myTask = new MyTask(number - 1);
        myTask.fork();
//        log.debug("{} 加上 {{}}", number, myTask.number);
        int result = number + myTask.join();
        return result;
    }


}
