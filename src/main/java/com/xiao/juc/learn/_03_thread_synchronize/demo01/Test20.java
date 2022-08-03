package com.xiao.juc.learn._03_thread_synchronize.demo01;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

/**
 * projectName: java-juc
 * <p>
 * author: aloneMan
 * <p>
 * createTime: 2022-07-10 18:58:11
 * <p>
 * description：保护性暂停
 */
@Slf4j
public class Test20 {
    public static void main(String[] args) {
        ProtectObject protectObject = new ProtectObject("https://www.google.com/");
        new Thread(() -> {
            protectObject.complete(null);
        }, "下载线程").start();
        new Thread(() -> {
            try {
                List<String> list = protectObject.get(2000L);
                log.debug(" {}", list != null ? list.size() : "获取超时");
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "查看线程").start();

    }
}


@Slf4j
class ProtectObject {
    private List<String> response = null;
    private static String URL = null;

    public ProtectObject(String url) {
        URL = url;
    }

    /**
     * @param timeout
     *         超市时间
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public synchronized List<String> get(Long timeout) throws IOException, InterruptedException {
        long begin = System.currentTimeMillis();
        long passedTime = 0;
        while (response == null) {
            long waitTime = timeout - passedTime;
            if (waitTime <= 0) {
                log.debug("获取页面超时...");
                break;
            }
            this.wait(timeout - passedTime);
            passedTime = System.currentTimeMillis() - begin;
        }

        return response;
    }

    public synchronized void complete(List<String> response) {
        this.response = response;
        this.notifyAll();
    }
}