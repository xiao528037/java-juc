package com.xiao.juc.learn._03_thread_synchronize.demo02;

import lombok.extern.slf4j.Slf4j;

/**
 * projectName: java-juc
 * <p>
 * author: aloneMan
 * <p>
 * createTime: 2022-07-11 07:46:22
 * <p>
 * description：保护对象
 */
@Slf4j
public class GuardObject<T> {
    private Integer id;

    private T message;

    public GuardObject(Integer id) {
        this.id = id;
    }

    /**
     * @param timeout
     *         超时时间
     * @return 如果没有超时，返回message，超时则会返回null
     */
    public synchronized T getMessage(Long timeout) {
        Long begin = System.currentTimeMillis();
        Long end = 0L;
        while (message == null) {
            timeout -= end;
            if (timeout <= 0) {
                log.debug("等待超时");
                break;
            }
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            end = System.currentTimeMillis() - begin;
        }
        return message;
    }

    /**
     * 设置邮件的信息，并将所有等待线程结束等待
     * @param message 信息内容
     */
    public synchronized void complete(T message) {
        this.message = message;
        this.notifyAll();
    }

    /**
     *
     * @return 返回当前线程的信息ID
     */
    public synchronized Integer getId() {
        return this.id;
    }


}
