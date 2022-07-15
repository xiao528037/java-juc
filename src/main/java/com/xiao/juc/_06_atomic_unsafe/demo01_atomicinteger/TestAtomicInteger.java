package com.xiao.juc._06_atomic_unsafe.demo01_atomicinteger;

import com.xiao.juc._06_atomic_unsafe.interface_packge.AtomicInterface;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-15 10:29:59
 * @description
 */
public class TestAtomicInteger<T> implements AtomicInterface<AtomicInteger> {
    private AtomicInteger money;

    public TestAtomicInteger(AtomicInteger money) {
        this.money = money;
    }

    public AtomicInteger getMoney() {
        return money;
    }

    @Override
    public void withdraw(AtomicInteger amount) {
        while (money.get() != 0) {
            int i = money.get();
            int i1 = i - amount.get();
            if (money.compareAndSet(i, i1)) {
                break;
            }
        }
    }

    @Override
    public void save(AtomicInteger amount) {

    }

    @Override
    public AtomicInteger get() {
        return null;
    }


}


