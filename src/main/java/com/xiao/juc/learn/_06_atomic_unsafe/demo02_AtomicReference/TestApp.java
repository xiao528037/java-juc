package com.xiao.juc.learn._06_atomic_unsafe.demo02_AtomicReference;

import com.xiao.juc.learn._06_atomic_unsafe.interface_packge.AtomicInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

import static com.xiao.juc.learn._06_atomic_unsafe.interface_packge.AtomicInterface.demo;
import static java.lang.Thread.sleep;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-15 15:35:43
 * @description 原子引用
 */
@Slf4j
public class TestApp {
    @Test
    public void test1() {

        DecimalAccountCas decimalAccountCas = new DecimalAccountCas(new BigDecimal(10000));
        demo(decimalAccountCas);
    }

    AtomicStampedReference<String> stampedReference = new AtomicStampedReference<>("A", 0);

    @Test
    public void testAtomicStampedReference() throws InterruptedException {
        int stamp = stampedReference.getStamp();
        String prev = stampedReference.getReference();
        other();
        sleep(1000);
        log.info("A -> B {}", stampedReference.compareAndSet(prev, "B", stamp, stamp + 1));
    }

    @Test
    public void testAtomicMarkableReference() throws InterruptedException {

        AtomicMarkableReference<GarbageBag> markableReference = new AtomicMarkableReference(new GarbageBag("垃圾袋满了"), true);
        new Thread(() -> {
            GarbageBag reference = markableReference.getReference();
            markableReference.compareAndSet(reference, reference.set("垃圾袋空了"), true, false);
        }).start();
        sleep(1000);
        GarbageBag reference = markableReference.getReference();
        GarbageBag replaceGarbage = new GarbageBag("更换垃圾袋");
        boolean b = markableReference.compareAndSet(reference, replaceGarbage, true, false);
        log.debug("{} ",b);

    }

    public void other() {
        int stamp = stampedReference.getStamp();
        String prev = stampedReference.getReference();
        log.info("A -> B {}", stampedReference.compareAndSet(prev, "B", stamp, stamp + 1));
        int stamp1 = stampedReference.getStamp();
        String prev1 = stampedReference.getReference();
        log.info("A -> C {}", stampedReference.compareAndSet(prev1, "C", stamp1, stamp1 + 1));
    }
}

class DecimalAccountCas implements AtomicInterface<BigDecimal> {

    private AtomicReference<BigDecimal> balance;

    public DecimalAccountCas(BigDecimal bigDecimal) {
        this.balance = new AtomicReference<>(bigDecimal);
    }

    @Override
    public void withdraw(BigDecimal bigDecimal) {
        BigDecimal prev, next;
        do {
            prev = balance.get();
            next = prev.subtract(bigDecimal);
        } while (!balance.compareAndSet(prev, next));
    }

    @Override
    public void save(BigDecimal bigDecimal) {

    }

    public BigDecimal get() {
        return balance.get();
    }
}

class GarbageBag {
    private String data;

    public GarbageBag(String data) {
        this.data = data;
    }

    public GarbageBag set(String data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "GarbageBag{" +
                "data='" + data + '\'' +
                '}';
    }
}