package com.xiao.juc.review._04_cas.unsafe;

import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-28 09:42:24
 * @description
 */
@Slf4j(topic = "unsafe")
public class UnsafeTest {

    private static Unsafe unsafe;

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe = (Unsafe) theUnsafe.get(null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws NoSuchFieldException {
        Student xiao = new Student("xiao", 24);
        Field name = Student.class.getDeclaredField("name");
//        name.setAccessible(true);
        long l = unsafe.objectFieldOffset(name);
        unsafe.compareAndSwapObject(xiao,l,"xiao","li");
        log.debug("{} ",xiao);
    }
}

class Student {
    private volatile String name;
    private volatile Integer age;

    public Student(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
