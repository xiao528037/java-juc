package com.xiao.juc._06_atomic_unsafe.demo01_atomicinteger;

import com.xiao.juc._06_atomic_unsafe.interface_packge.AtomicInterface;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-15 10:29:59
 * @description
 */
public class TestAtomicInteger implements AtomicInterface {
    private AtomicInteger money;

    public TestAtomicInteger(AtomicInteger money) {
        this.money = money;
    }

    public AtomicInteger getMoney() {
        return money;
    }

    @Override
    public void withdraw(Integer amount) {
        while (money.get() != 0) {
            int i = money.get();
            int i1 = i - amount;
            if (money.compareAndSet(i, i1)) {
                break;
            }
        }
    }

    @Override
    public void save(Integer amount) {

    }
}


