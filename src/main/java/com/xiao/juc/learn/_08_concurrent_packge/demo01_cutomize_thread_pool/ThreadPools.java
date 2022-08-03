package com.xiao.juc.learn._08_concurrent_packge.demo01_cutomize_thread_pool;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-16 17:17:28
 * @description 线程池
 */

@Slf4j(topic = "c.pools")
public class ThreadPools<T extends TaskInterface> {
    /**
     * 任务队列
     */
    private BlockingQueue<T> taskQueue = new BlockingQueue<>(2);

    /**
     * 线程集合
     */
    private HashSet<ExecuteTask> runTasks = new HashSet<>();
    /**
     * 核心线程数量
     */
    private Integer coreSize = 2;
    /**
     * 最大线程数量
     */
    private Integer maxSize = 2;
    /**
     * 获取任务超时时间，默认1000纳秒
     */
    private Long timeout = 1000L;
    /**
     * 时间单位，默认是纳秒
     */
    private TimeUnit timeUnit = TimeUnit.NANOSECONDS;

    /**
     * 等待超时的执行策略
     */
    private RejectPolicy<T> rejectPolicy;

    private AtomicBoolean[] atomicBooleans;

    /**
     * 初始化
     */
    public ThreadPools(Integer coreSize, Integer maxSize, RejectPolicy rejectPolicy) {
        this.rejectPolicy = rejectPolicy;
        this.coreSize = coreSize;
        this.maxSize = maxSize;
    }

    /**
     * 执行任务
     */
    public void execute(T t) throws InterruptedException {
        Integer b;
        //当核心线程已满，进行扩容
        if (runTasks.size() == coreSize && runTasks.size() < maxSize) {
            //当扩容后小于等于最大线程数的一半时
            if ((b = runTasks.size() << 1) <= (maxSize >> 1)) {
                coreSize = b;
                log.debug(">>> 1执行扩容 <<< coreSize:{}", coreSize);
            } else {
                //当扩容后大于等于最大线程数的一半时,直接将最大值赋值给核心线程数
                coreSize = maxSize;
                log.debug(">>> 2执行扩容 <<< coreSize:{}", coreSize);
            }
        }
        if (coreSize.equals(maxSize)) {//已经达到最大扩容
            //log.info("{} 线程扩容失败,当前线程池已经扩容到最大! coreSize {}", Thread.currentThread().getName(), coreSize);
        }
        //当容量充足时
        synchronized (taskQueue) {
            if (runTasks.size() < coreSize) {
                ExecuteTask<T> executeTask = new ExecuteTask<>(t);
                runTasks.add(executeTask);
                executeTask.start();
            } else {
                //放入任务队列队列中
//                boolean isSuccess = taskQueue.setTask(t);
                taskQueue.tryPut(rejectPolicy, t);

            }
        }
    }



    class ExecuteTask<T extends TaskInterface> extends Thread {

        private T t;

        public ExecuteTask(T t) {
            this.t = t;
        }


        @Override
        public void run() {
            //函数表达式不为空执行函数或者getTask获取任务，也不为空，则执行函数
            while (t != null || (t = (T) taskQueue.getTask()) != null) {
                t.implementTask();
                t = null;
            }
            synchronized (runTasks) {
//                log.debug("当前线程结束");
                runTasks.remove(this);
            }

        }
    }
}
