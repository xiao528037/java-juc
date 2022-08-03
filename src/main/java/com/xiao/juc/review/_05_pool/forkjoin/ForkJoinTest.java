package com.xiao.juc.review._05_pool.forkjoin;

import com.sun.corba.se.impl.encoding.CDRInputStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountedCompleter;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-31 17:15:10
 * @description
 */
@Slf4j(topic = "forkJoin")
public class ForkJoinTest {
    @Test
    public void t1() {
        ForkJoinPool forkJoinPool = new ForkJoinPool(7);

        Integer invoke = forkJoinPool.invoke(new MyTask(1, 7));
        log.debug("{} ",invoke);
    }
}

class MyTask extends RecursiveTask<Integer> {
    private int begin;
    private int end;

    public MyTask(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if (end == begin) {
            return end;
        }
        if (end - begin == 1) {
            return end + begin;
        }
        int median = (end + begin) / 2;
        MyTask t1 = new MyTask(begin, median);
        MyTask t2 = new MyTask(median + 1, end);
        t1.fork();
        t2.fork();
        return t1.join() + t2.join();
    }
}
class MyTask2 extends RecursiveAction{
    @Override
    protected void compute() {
    }
}
