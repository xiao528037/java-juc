package com.xiao.juc.review._02_synchronize.asynchronous_model.version02;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-25 14:15:09
 * @description 消息队列
 */
@Slf4j(topic = "消息队列")
public class MessageQueue<T> {
    private ReentrantLock reentrantLock = new ReentrantLock();
    private Condition condition = reentrantLock.newCondition();
    private LinkedList<T> messageQueue = new LinkedList<>();

    private Integer capacity;

    public MessageQueue(int capacity) {
        this.capacity = capacity;
    }


    /**
     * 拥有超时时间的获取消息
     *
     * @param milli
     *         等待多久
     * @return 返回消息队列中的消息
     */
    public T getMessage(Long milli) {
        reentrantLock.lock();
        try {
            Long begin = System.currentTimeMillis();
            Long waitTime = 0L;
            while (messageQueue.isEmpty()) {
                milli = milli - waitTime;
                if (milli <= 0) {
                    log.debug("{} ", "等待超时...");
                    return null;
                }
                condition.await(milli, TimeUnit.MILLISECONDS);
                waitTime = System.currentTimeMillis() - begin;
            }
            T first = messageQueue.removeFirst();
            condition.signalAll();
            return first;

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            reentrantLock.unlock();
        }
    }

    /**
     * 向消息队列添加消息
     *
     * @param t
     *         消息
     * @param milli
     *         等待时长
     * @return 是否添加成功
     */
    public boolean setMessage(T t, Long waitTime) {
        reentrantLock.lock();
        try {
            Long begin = System.currentTimeMillis();
            Long use = 0L;
            while (messageQueue.size() == capacity) {
                waitTime = waitTime - use;
                if (waitTime <= 0) {
                    log.debug("{} ", "队列满...");
                    return false;
                }
                condition.await();
                use = System.currentTimeMillis() - begin;
            }
            boolean add = messageQueue.add(t);
            condition.signalAll();
            log.debug("添加消息成功...");
            return add;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            reentrantLock.unlock();
        }
    }
}
