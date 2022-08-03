package com.xiao.juc.learn._03_thread_synchronize.demo03;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

/**
 * projectName: java-juc
 * <p>
 * author: aloneMan
 * <p>
 * createTime: 2022-07-11 15:02:16
 * <p>
 * description：消息队列
 */
@Slf4j
public class MessageQueue<T> {

    private LinkedList<Message<T>> queue = new LinkedList<>();

    private int capacity;

    public MessageQueue(int capacity) {
        this.capacity = capacity;
    }

    /**
     * 获取消息，当消息队列为空时，线程进入等待，等待put()中的notifyAll唤醒,不为空时返回头部的Message
     */
    public Message<T> take() {
        synchronized (queue) {
            while (queue.isEmpty()) {
                try {
                    log.debug("队列为空等待消息...");
                    queue.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            Message<T> message = queue.removeFirst();
            queue.notifyAll();
            return message;
        }
    }

    /**
     * 当队列满的时候，线程进入等待，等待take方法中notifyAll唤醒
     *
     * @param message
     *         存入队列中的对象
     */
    public void put(Message<T> message) {
        synchronized (queue) {
            while (queue.size() >= capacity) {
                try {
                    log.debug("{}线程添加操作失败,队列已满,等待其他线程释放队列信息...", Thread.currentThread().getName());
                    queue.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            queue.addLast(message);
            queue.notifyAll();
        }
    }
}
