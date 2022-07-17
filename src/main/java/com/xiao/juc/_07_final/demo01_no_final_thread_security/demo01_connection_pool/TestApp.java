package com.xiao.juc._07_final.demo01_no_final_thread_security.demo01_connection_pool;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-16 15:17:27
 * @description
 */
@Slf4j
public class TestApp {
    @Test
    public void t1() throws InterruptedException {
        CustomizeConnectionPool customizeConnectionPool = new CustomizeConnectionPool(2);
        ArrayList<Thread> threadsPool=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            threadsPool.add(new Thread(() -> {
                CustomizeConnection connection = customizeConnectionPool.takeConnection();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                customizeConnectionPool.backConnection(connection);
            }));
        }
        threadsPool.forEach(Thread::start);
        threadsPool.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

    }
}
