package com.xiao.juc._03_thread_synchronize.demo03;

/**
 * projectName: java-juc
 * <p>
 * author: aloneMan
 * <p>
 * createTime: 2022-07-11 15:02:42
 * <p>
 * description：消息类
 */
final public class Message<T> {
    private Integer id;
    private T message;

    public Message(Integer id, T message) {
        this.id = id;
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public T getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", message=" + message +
                '}';
    }
}
