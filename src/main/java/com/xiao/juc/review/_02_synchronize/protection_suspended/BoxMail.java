package com.xiao.juc.review._02_synchronize.protection_suspended;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.concurrent.locks.LockSupport;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-24 22:21:52
 * @description 邮箱
 */
@Slf4j(topic = "邮箱")
public class BoxMail {

    private static Object lock = new Object();

    private static HashMap<Integer, String> boxMail = new HashMap<>();


    /**
     * 获取邮件信息
     *
     * @param id
     *         用户id
     * @return 返回邮件内容
     */
    public static String takeMail(Integer id, Long milli) {
        synchronized (lock) {
            Long begin = System.currentTimeMillis();
            long lastTime = 0L;
            while (boxMail.get(id) == null) {
                milli = milli - lastTime;
                if (milli <= 0) {
                    log.debug("{} 等待超时", id);
                    boxMail.put(id, null);
                    return null;
                }
                try {
                    lock.wait(milli);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                lastTime = System.currentTimeMillis() - begin;
            }

            return boxMail.remove(id);
        }
    }

    /**
     * 送信
     *
     * @param id
     *         送给指定的用户
     * @param content
     *         邮件的内容
     */
    public static void deliverMail(Integer id, String content) {
        synchronized (lock) {
            if (boxMail.containsKey(id) && boxMail.remove(id) == null) {
                log.debug("{} 此用户邮箱过期", id);
            } else {
                log.debug(">>> 收信人 {} 新建内容 {} 放入邮箱", id, content);
                boxMail.put(id, content);
            }

            lock.notifyAll();
        }

    }

}
