package com.xiao.juc.learn.demo01_no_final_thread_security.demo01_connection_pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-16 14:36:25
 * @description 自定义线程链接池
 */
@Slf4j(topic = "c.t")
public class CustomizeConnectionPool {

    private ReentrantLock lock;
    private Condition con;
    private Integer poolSize;

    private CustomizeConnection[] connectionPool;

    private AtomicBoolean[] isBusy;

    private CustomizeConnectionPool() {
    }

    /**
     * 初始化
     *
     * @param poolSize
     *         连接池大小
     */
    public CustomizeConnectionPool(Integer poolSize) {
        this.poolSize = poolSize;
        connectionPool = new CustomizeConnection[poolSize];
        isBusy = new AtomicBoolean[poolSize];
        initConnection(poolSize);
    }

    /**
     * 初始化连接池
     *
     * @param poolSize
     *         连接池大小
     */
    private void initConnection(Integer poolSize) {
        synchronized (this) {
            for (int i = 0; i < poolSize; i++) {
                connectionPool[i] = new CustomizeConnection("链接为" + i);
                isBusy[i] = new AtomicBoolean(false);
            }
            this.lock = new ReentrantLock();
            this.con = this.lock.newCondition();
        }
    }

    /**
     * 获取链接
     *
     * @return 链接对象
     */
    public CustomizeConnection takeConnection() {
        while (true) {
            //遍历线程池
            for (int i = 0; i < poolSize; i++) {
                //判断当前线程是否忙碌
                if (!isBusy[i].get()) {
                    //将此链接设置为忙碌状态，如果成功则返回当前链接，
                    if (isBusy[i].compareAndSet(false, true)) {
                        log.debug("{} 链接借出成功", connectionPool[i]);
                        return connectionPool[i];
                    }
                }
            }
            //如果所有链接多处于忙碌状态，让当前线程进入等待，如果等待失败抛出一个异常
            /*synchronized (this) {
                try {
                    this.wait(500);
                } catch (InterruptedException e) {
                    log.error("{} 线程获取链接失败", Thread.currentThread().getName());
                    return null;
                }
            }*/
            lock.lock();
            try {
                boolean await = con.await(5, TimeUnit.SECONDS);
                if (!await) {
                    log.error("等待超时");
                    return null;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }
    }

    public void backConnection(CustomizeConnection connection) {
        for (int i = 0; i < poolSize; i++) {
            //找到要回收的链接并将其设置为false
            if (connectionPool[i] == connection) {
                boolean b = isBusy[i].compareAndSet(true, false);
                if (b) {
               /*     synchronized (this) {
                        log.info("{} 链接回收成功", connection);
                        this.notifyAll();
                    }*/
                    lock.lock();
                    try {
                        log.info("{} 链接回收成功", connection);
                        con.signalAll();
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }

    }
}
