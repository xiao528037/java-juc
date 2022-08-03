package com.xiao.juc.learn._03_thread_synchronize.demo02;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/**
 * projectName: java-juc
 * <p>
 * author: aloneMan
 * <p>
 * createTime: 2022-07-11 08:05:44
 * <p>
 * description：邮箱
 */
public class MailBoxes {
    private static Map<Integer, GuardObject> mailBoxes = new Hashtable<>();

    private static int KEY_INDEX = 1;

    //生成key
    public static synchronized int getId() {
        return KEY_INDEX++;
    }

    //发邮件
    public static <T> GuardObject<T> createGuardedObject() {

        GuardObject<T> tGuardObject = new GuardObject<>(getId());
        mailBoxes.put(tGuardObject.getId(), tGuardObject);
        return tGuardObject;


    }

    //获取邮件信息
    public static GuardObject getGuardedById(int id) {
        return mailBoxes.remove(id);
    }

    //获取所有的Ids
    public static Set<Integer> getGuardedByIds() {
        return mailBoxes.keySet();
    }
}
