package com.xiao.juc._08_concurrent_packge.demo03;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RecursiveTask;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-17 21:42:28
 * @description
 */
@Slf4j
public class MyTaskTwo extends RecursiveTask<Integer> {
    private Integer begin;
    private Integer end;

    public MyTaskTwo(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }


    /**
     * 分治
     *
     * @return 计算结果
     */
    @Override
    protected Integer compute() {
        //当begin等于end,则直接返回
        if (begin.equals(end)) {
//            log.debug("begin {} -- end {}", begin, end);
            return end;
        }
        if (end - begin == 1) {
//            log.debug("begin {} -- end {}", begin, end);
            return end + begin;
        }
        //如何计算begin 和 end
        int tmp = (begin + end) / 2;
        MyTaskTwo myTaskTwo = new MyTaskTwo(begin, tmp);
        MyTaskTwo myTaskTwo1 = new MyTaskTwo(tmp + 1, end);
        myTaskTwo.fork();
        myTaskTwo1.fork();
        return myTaskTwo.join() + myTaskTwo1.join();
    }
}
