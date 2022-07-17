package com.xiao.juc._08_concurrent_packge.demo01;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-16 16:23:57
 * @description 阻塞队列
 */
@Slf4j(topic = "c.block")
public class BlockingQueue<T> {
    /**
     * 这是一个阻塞队列
     */
    private Deque<T> tDeque;

    /**
     * 锁对象
     */
    private ReentrantLock lock;

    /**
     * 阻塞队列满时，线程等待的区域
     */
    private Condition fullWaitSet;

    /**
     * 阻塞队列为空时，线程等待的区域
     */
    private Condition emptyWaitSet;

    /**
     * 阻塞队列的大小，默认是16
     */
    private Integer capcity = 16;

    /**
     * 线程默认等待时长
     */
    private Long waitTime = 1000L;

    private TimeUnit timeUnit = TimeUnit.MILLISECONDS;

    public BlockingQueue() {
        initBlockingQueue();
    }

    /**
     * 设置大小的阻塞队列
     *
     * @param capcity
     *         阻塞队列的容量
     */
    public BlockingQueue(Integer capcity) {
        this.capcity = capcity;
        initBlockingQueue();
    }

    /**
     * 初始化相关信息
     */
    private void initBlockingQueue() {
        this.tDeque = new ArrayDeque<>(capcity);
        this.lock = new ReentrantLock();
        this.emptyWaitSet = lock.newCondition();
        this.fullWaitSet = lock.newCondition();
    }

    /**
     * 获取任务
     *
     * @param nanoTime
     *         超时时间，默认为纳秒
     */
    public T getTask(Long nanoTime) {
        lock.lock();
        try {
            //被唤醒后队列还为空，则需要等待的时间
            while (true) {
                if (isEmpty()) {
                    //如果队列为空则
                    try {
                        boolean isTimeout = emptyWaitSet.await(nanoTime, timeUnit);
                        if (!isTimeout) {
                            log.error("任务队列为空");
                            return null;
                        }
                        if (tDeque.size() == 0) {
                            return null;
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                //如果队列不为空,获取尾部的任务
                T task = tDeque.removeFirst();
//                log.debug("<<< 删除任务,任务长度为: {}", tDeque.size());
                //唤醒一个在队列满的时候，添加任务的线程
                fullWaitSet.signal();
                return task;
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * 向队列添加一个任务
     *
     * @param task
     *         需要线程去执行的任务
     * @param waitTime
     *         等待超时的时间
     * @return ture添加成功 false添加失败
     */
    public boolean setTask(T task, Long waitTime) {
        lock.lock();
        try {
            if (isFull()) {
                //如果队列满了，进入等待
                boolean isTimeout = fullWaitSet.await(waitTime, timeUnit);
                if (!isTimeout) {
                    log.debug("任务队列已满");
                    return false;
                }
            }
            //把任务添加到队列头
            tDeque.addLast(task);
            //唤醒一个队列为空时进入等待的线程
            emptyWaitSet.signal();
            return true;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 默认超时时间
     *
     * @param t
     *         需要执行的任务
     * @return ture添加成功 false添加失败
     */
    public boolean setTask(T t) {
        return setTask(t, this.waitTime);
    }

    /**
     * 线程默认的等待时间
     *
     * @return 返回任务
     */
    public T getTask() {
        return getTask(this.waitTime);
    }

    /**
     * 判断阻塞队列是否为空
     *
     * @return 为空true, 否则为false
     */
    public Boolean isEmpty() {
        lock.lock();
        try {
            return getSize() == 0;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 判断对垒是否已满
     *
     * @return 满了则返回true, four则为false
     */
    public boolean isFull() {
        lock.lock();
        try {
            return getSize().equals(capcity);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 获取阻塞队列容量
     *
     * @return 返回阻塞队列的容量
     */
    public Integer getSize() {
        lock.lock();
        try {
            return tDeque.size();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 拥有拒绝策略的任务添加
     *
     * @param rejectPolicy
     *         函数式子操作
     * @param task
     *         任务操作
     */
    public void tryPut(RejectPolicy<T> rejectPolicy, T task) {
        lock.lock();
        try {
            //再次进行判断队列是否满了
            if (isFull()) {
                //如果满了执行策略
                rejectPolicy.reject(this, task);
            } else {
                 setTask(task);
            }
        } finally {
            lock.unlock();
        }
    }
}
