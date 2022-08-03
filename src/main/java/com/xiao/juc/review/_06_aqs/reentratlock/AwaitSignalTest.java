package com.xiao.juc.review._06_aqs.reentratlock;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-08-02 09:40:48
 * @description
 */
@Slf4j(topic = "await-signal")
public class AwaitSignalTest {
    private ReentrantLock myLock = new ReentrantLock();
    private Condition condition = myLock.newCondition();

    @Test
    public void t1() throws InterruptedException {

        Thread t1 = new Thread(() -> {
            myLock.lock();
            try {
                log.debug("{} {}", Thread.currentThread().getName(), "获取到锁，放入条件变量等待...");
                condition.await();
                log.debug("{} {}", Thread.currentThread().getName(), "成功被唤醒...");
            } catch (InterruptedException e) {
                log.debug("{} ", "被打断了...");
                return;
            } finally {
                myLock.unlock();
            }
        });
        t1.start();
        TimeUnit.SECONDS.sleep(2);
        myLock.lock();
        try {
            condition.signal();
        } finally {
            myLock.unlock();
        }


        t1.join();
    }

    /**
     * 实现一下condition的删除非-2的队列
     */
    @Test
    public void t2() {
        Node node = new Node();
        node.add(-1);
        node.add(-1);
        node.add(-2);
        node.add(-2);
        node.add(-1);
        node.add(-1);
        node.deleteCancel();
        log.debug("{} ", node);
    }

    @Test
    public void t3() {
        log.debug("{} ", true ? 1 : 0);
    }
}

@Slf4j(topic = "test")
class Node {
    private Integer status;

    private Node firstNode;

    private Node lastNode;

    private Node nextNode;

    private Node(Integer status) {
        this.status = status;
    }

    public Node() {
    }


    public void add(Integer status) {
        if (firstNode == null) {
            this.status = status;
            this.firstNode = this;
            this.lastNode = this;
        } else {
            Node node = new Node(status);
            this.lastNode.nextNode = node;
            this.lastNode = node;
            node.firstNode = this.firstNode;
            node.lastNode = this.lastNode;
            node.nextNode = lastNode.nextNode;
        }
    }

    public void deleteCancel() {
        Node t = firstNode;
        Node trail = null;
        while (t != null) {
            Node next = t.nextNode;
            if (t.status != -2) {
                t.nextNode = null;
                if (trail == null) {
                    this.firstNode = next;
                } else {
                    trail.nextNode = next;
                }
                if (next == null) {
                    this.lastNode = trail;
                }
            } else {
                trail = t;
            }
            t = next;
        }
    }

    @Override
    public String toString() {
        return "Node{" +
                "status=" + status +
                ", nextNode=" + nextNode +
                '}';
    }
}
