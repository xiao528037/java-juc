package com.xiao.juc.learn._08_concurrent_packge.demo01_cutomize_thread_pool;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-17 09:45:06
 * @description 拒绝策略，当线程添加失败时的执行策略
 */
@FunctionalInterface
public interface RejectPolicy<T> {
    void reject(BlockingQueue<T> queue, T task);
}
