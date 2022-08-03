package com.xiao.juc.learn._08_concurrent_packge.demo06_concurrenthashmap;

import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.Current;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-23 17:23:35
 * @description
 */
@Slf4j(topic = "read")
public class ReadFile {
    public static <T> void read(Supplier<Map<String, T>> consumer, BiConsumer<Map<String, T>, List<String>> biConsumer) throws InterruptedException {
        Map<String, T> stringTMap = consumer.get();
        for (int i = 1; i <= 200; i++) {
            int j = i;
            new Thread(() -> {
                try {
                    FileInputStream fileInputStream = new FileInputStream("/Users/xiaojiebin/IdeaProjects/java-juc/src/main/java/com/xiao/juc/learn/_08_concurrent_packge/demo06_concurrenthashmap/txt/" + j + ".txt");
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                    String str = null;
                    ArrayList<String> strings = new ArrayList<>();
                    while ((str = bufferedReader.readLine()) != null)
                        strings.add(str);
                    biConsumer.accept(stringTMap, strings);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
        TimeUnit.SECONDS.sleep(5);
        log.debug(stringTMap.toString());
    }


    public static void main(String[] args) throws InterruptedException {
        read(
                () -> new ConcurrentHashMap<String, LongAdder>(),
                (map, list) -> {
                    list.forEach(item -> {
                        LongAdder longAdder = map.computeIfAbsent(item, key -> new LongAdder());
                        longAdder.increment();
                    });
                });
    }
}
