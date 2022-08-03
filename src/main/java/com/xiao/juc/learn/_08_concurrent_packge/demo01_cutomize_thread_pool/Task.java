package com.xiao.juc.learn._08_concurrent_packge.demo01_cutomize_thread_pool;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-16 17:40:47
 * @description 执行的任务
 */
public class Task<T, R> implements TaskInterface<R> {
    private Function<T, R> performTasks;

    private Supplier<T> parameter;

    public Task(Supplier<T> parameter, Function<T, R> performTasks) {
        this.performTasks = performTasks;
        this.parameter = parameter;
    }

    private void setTask(Supplier<T> parameter, Function<T, R> performTasks) {
        this.performTasks = performTasks;
        this.parameter = parameter;
    }

    @Override
    public R implementTask() {
        return performTasks.apply(parameter.get());
    }
}
