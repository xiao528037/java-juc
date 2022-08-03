package com.xiao.juc.learn._06_atomic_unsafe.demo04_field;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-15 18:31:15
 * @description
 */
@Slf4j
public class TestApp {
    @Test
    public void t1() {
        Person person = new Person();
        AtomicReferenceFieldUpdater<Person, String> fieldUpdater = AtomicReferenceFieldUpdater.newUpdater(Person.class, String.class, "name");
        boolean isSuccess = fieldUpdater.compareAndSet(person, null, "张三");
        log.debug("{}", isSuccess);
    }
}
