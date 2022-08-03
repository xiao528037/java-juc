package com.xiao.juc.review._01_thread;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.xiao.juc.review._01_thread.common.MyCallable;
import com.xiao.juc.review._01_thread.common.MyFirstThread;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import static java.lang.Thread.interrupted;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-21 16:20:42
 * @description 复习线程的创建
 */
@Slf4j(topic = "x.review.test")
public class JUC_01_FirstThread {

    /**
     * 通过实例Thread类创建线程
     */
    @Test
    public void createThread01() {
        MyFirstThread myFirstThread = new MyFirstThread();
        myFirstThread.start();
    }

    /**
     * 通过匿名内部类实现
     */
    @Test
    public void creatThread02() {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                log.debug("线程输出 {}", i);
            }
        }, "t1");
        thread.start();
    }

    /**
     * 通过FutureTask装饰Callable类来实现线程的创建，以及获取线程的返回值
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void createThread03() throws ExecutionException, InterruptedException {
        MyCallable<String> callable = new MyCallable<>();
        callable.setValue("你好我的callable.");
        FutureTask<String> callableFutureTask = new FutureTask((Callable) callable);
        new Thread(callableFutureTask, "callable").start();

        String message = callableFutureTask.get();
        log.debug("message >> {}", message);
    }

    /**
     * run()和start()的区别
     */
    @Test
    public void threadApiStartAndRun() throws InterruptedException {
        //调用run只会执行其中的run方法，不会开启一个新的线程
        new Thread(() -> {
            log.debug("{} 线程执行了...", Thread.currentThread().getName());
        }, "t1").run();
        //调用start启动一个新的线程，并且不能抛出异常，因为Runnable和Thread中的run方法没有抛出异常！
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("{} 线程执行了...", Thread.currentThread().getName());
        }, "t2").start();

        Thread.sleep(2000);
    }


    /**
     * sleep and yield（无法体现）sleep会1秒后执行
     */
    int i = 0;

    @Test
    public void threadSleepAndYield() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            Thread.yield();
            log.debug("{} 线程执行了 {}", Thread.currentThread().getName(), ++i);
        }, "t1");
        Thread t2 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("{} 线程一秒后执行了 {}", Thread.currentThread().getName(), ++i);
        }, "t2");
        t1.start();
        t2.start();

    }

    @Test
    public void threadWait() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("{} 线程执行了", Thread.currentThread().getName());
        }, "t1");
        Thread t2 = new Thread(() -> {
            log.debug("{} 线程执行了", Thread.currentThread().getName());
        }, "t2");
        t1.start();
        log.debug("开始join...");
        t1.join(2000);
        log.debug("join结束...");
        t2.start();
        Thread.sleep(6000);
    }

    @Test
    public void reverseTest() {
        ListNode node4 = new ListNode(4);
        ListNode node3 = new ListNode(3, node4);
        ListNode node2 = new ListNode(2, node3);
        ListNode node1 = new ListNode(1, node2);
        reverse(node1);
        log.debug("{}", node1);
    }

    /**
     * 打断 interrupt() 打断成功，将标记设置成true，使用interrupted()会把标记重置为false
     *
     * @throws InterruptedException
     */
    @Test
    public void threadInterrupt() throws InterruptedException {

        Thread t1 = new Thread(() -> {
            while (true) {
                Thread current = Thread.currentThread();
                boolean interrupted = current.isInterrupted();
                if (interrupted) {
                    log.debug(" 打断状态 {}", interrupted);
                    break;
                }
            }

        }, "t1");
        t1.start();
        log.debug("{}", t1.isInterrupted());
        Thread.sleep(500);
        t1.interrupt();//打断
        t1.join();
    }

    @Test
    public void threadInterruptSleep() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                log.debug("状态 {}", Thread.currentThread().isInterrupted());
                throw new RuntimeException(e);
            }
        }, "t1");
        t1.start();
        log.debug("状态 {}", t1.isInterrupted());
        Thread.sleep(2000);
        t1.interrupt();
    }

    /**
     * 当使用park被打断后，打断标记不会被清理，只能通过Thread.interrupted()手动清理，
     *
     * @throws InterruptedException
     */
    @Test
    public void threadPark() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("打断状态 {}", Thread.currentThread().isInterrupted());
            log.debug("park...");
            LockSupport.park();
            log.debug("unpark...");
            log.debug("清理标记之前,打断状态：{}", Thread.currentThread().isInterrupted());
            Thread.interrupted();//清除打断标记
        }, "t1");
        t1.start();
        Thread.sleep(2000);
        t1.interrupt();
        t1.join();
        log.debug("清除打断标记之后 {}", t1.isInterrupted());
    }

    @Test
    public void threadDaemon() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int j = 0; j < 10000; j++) {
                log.debug("-- {}", j);
            }
        }, "t1");
        t1.start();
        t1.join();
    }

    public ListNode reverse(ListNode head) {
        ListNode pointer = head;
        ListNode prev = null;//作用记录当前链表的head
        while (pointer.next != null) {
            prev = head;
            head = pointer.next;
            pointer.next = head.next;
            head.next = prev;
        }
        return head;
    }
}

class ListNode {
    int value;
    ListNode next;

    public ListNode(int value) {
        this.value = value;
    }

    public ListNode(int value, ListNode next) {
        this.value = value;
        this.next = next;
    }

    @Override
    public String toString() {
        return "ListNode{" +
                "value=" + value +
                ", next=" + next +
                '}';
    }
}
