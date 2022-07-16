package com.xiao.juc._06_atomic_unsafe.demo07_unsafe;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-16 08:02:49
 * @description
 */
@Slf4j
public class TestApp {
    @Test
    public void test() throws NoSuchFieldException, IllegalAccessException {
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe unsafe = (Unsafe) theUnsafe.get(null);
        log.debug("{}", unsafe);
        long idOffset = unsafe.objectFieldOffset(Student.class.getDeclaredField("age"));
        long nameOffset = unsafe.objectFieldOffset(Student.class.getDeclaredField("name"));
        Student student = new Student();
        int age;
        do {
            age = student.getAge();
        } while (!unsafe.compareAndSwapInt(student, idOffset, age, 24));

        unsafe.compareAndSwapObject(student, nameOffset, null, "肖杰斌");
        log.debug("{}", student);


    }
}
