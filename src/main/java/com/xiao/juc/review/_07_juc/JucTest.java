package com.xiao.juc.review._07_juc;

import com.xiao.juc.review._06_aqs.CyclicBarrier.CustomizeThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import static org.checkerframework.checker.units.UnitsTools.s;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-08-03 16:25:50
 * @description
 */
@Slf4j(topic = "juc-t")
public class JucTest {
    @Test
    public void t1() {
        Hashtable<String, String> map = new Hashtable<>();
        map.put("null", "123");
        HashMap<String, String> map1 = new HashMap<>();
        map1.put(null,null);

        new ScheduledThreadPoolExecutor(10,new CustomizeThreadFactory("task-1"));
    }
}
