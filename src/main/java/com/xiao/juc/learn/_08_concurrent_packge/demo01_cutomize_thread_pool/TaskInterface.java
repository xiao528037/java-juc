package com.xiao.juc.learn._08_concurrent_packge.demo01_cutomize_thread_pool;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-16 17:47:41
 * @description
 */
public interface TaskInterface<T> {
    public T implementTask();
}
