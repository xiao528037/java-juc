package com.xiao.juc.learn._06_atomic_unsafe.interface_packge;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-15 10:31:55
 * @description
 */


public interface AtomicInterface<T> {

    public void withdraw(T t);

    public void save(T t);

    public T get();
    static void demo(AtomicInterface atomicInterface) {
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            threads.add(new Thread(() -> {
                atomicInterface.withdraw(BigDecimal.TEN);
            }));
        }
        threads.forEach(Thread::start);
        threads.forEach(item->{
            try {
                item.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println(atomicInterface.get());
    }
}
