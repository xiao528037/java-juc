package com.xiao.juc.review._01_thread.common;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-21 16:44:46
 * @description 线程任务类
 */
@Slf4j(topic = "x.review.callable")
public class MyCallable<T> implements Callable<T> {
    private T value;

    public void setValue(T t) {
        this.value = t;
    }

    @Override
    public T call() throws Exception {
        log.debug("MyCallable 执行了...");
        return value;
    }
}
