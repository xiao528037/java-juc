package com.xiao.juc._07_final.demo01_no_final_thread_security;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-16 09:13:15
 * @description
 */
@Slf4j
public class DateFormatThreadSecurity {


    @Test
    public void test01() {
        Long aLong = new Long(1);
        Long.valueOf(1);


        //加锁实现线程安全
//        accomplish01();
        //final实现线程安全
//        accomplish02();
    }


    private void accomplish02() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                System.out.println(dateTimeFormatter.parse("2022-06-29"));
            }).start();
        }
    }

    private void accomplish01() {
        ReentrantLock reentrantLock = new ReentrantLock();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                reentrantLock.lock();
                try {
                    log.debug("{}", simpleDateFormat.parse("2022-06-29"));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                } finally {
                    reentrantLock.unlock();
                }
            }).start();
        }
    }
}
