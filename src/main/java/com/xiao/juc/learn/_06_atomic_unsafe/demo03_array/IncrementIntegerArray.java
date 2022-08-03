package com.xiao.juc.learn._06_atomic_unsafe.demo03_array;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-15 17:14:11
 * @description
 */
@Slf4j
public class IncrementIntegerArray<T> {
    private Supplier<T> arraySupplier;
    private Function<T, Integer> lengthFun;
    private BiConsumer<T, Integer> puConsumer;
    private Consumer<T> printConsumer;
    private ArrayList<Thread> threads = new ArrayList<>();

    public IncrementIntegerArray(Supplier<T> arraySupplier,
                                 Function<T, Integer> lengthFun,
                                 BiConsumer<T, Integer> putConsumer,
                                 Consumer<T> printConsumer) {
        this.arraySupplier = arraySupplier;
        this.lengthFun = lengthFun;
        this.printConsumer = printConsumer;
        this.puConsumer = putConsumer;
    }

    private IncrementIntegerArray() {
    }

    public void start() {

            T array = arraySupplier.get();
            Integer length = lengthFun.apply(array);
            for (int i = 0; i < length; i++) {
                threads.add(new Thread(() -> {
                    for (int j = 0; j < 10000; j++) {
                        puConsumer.accept(array, j % length);
                    }
                }, "t" + i));
            }
            threads.forEach(Thread::start);
            threads.forEach(thread -> {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            printConsumer.accept(array);
    }

}
