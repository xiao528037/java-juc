package com.xiao.juc.review._04_cas;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-26 21:49:10
 * @description
 */
@Slf4j(topic = "原子类测试")
public class AtomicIntegerTest {


    @Test
    public void t1() {
        AtomicInteger atomicInteger = new AtomicInteger();
        boolean b = atomicInteger.compareAndSet(atomicInteger.get(), 2);
        atomicInteger.getAndUpdate((prev) -> {
            return 10 / prev;
        });
        log.debug("{} ", atomicInteger.get());
    }

    @Test
    public void atomicLong() {
        AtomicLong atomicLong = new AtomicLong();
        atomicLong.getAndIncrement();
        atomicLong.decrementAndGet();
        atomicLong.compareAndSet(atomicLong.get(), 10000L);
        log.debug("{} ", atomicLong.get());
    }

    @Test
    public void atomicBoolean() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        atomicBoolean.compareAndSet(atomicBoolean.get(), false);
        log.debug("{} ", atomicBoolean.get());

    }

    @Test
    public void atomicReference() {
        AtomicReference<Student> atomicReference = new AtomicReference<>();
        Student xiao = new Student("xiao", 12);
        atomicReference.set(xiao);
        Student gouhai;
        do {
            gouhai = new Student("gouhai", 12);
        } while (!atomicReference.compareAndSet(atomicReference.get(), gouhai));
        log.debug("{} ", atomicReference.get());
    }

    @Test
    public void atomicStampedReference() {
        AtomicStampedReference<Student> stampedReference = new AtomicStampedReference<Student>(new Student("xiao", 123), 1);
        Student legal_high;
        int stamp;
        do {
            legal_high = new Student("legal high", 27);
        } while (!stampedReference.compareAndSet(stampedReference.getReference(), legal_high, stamp = stampedReference.getStamp(), stamp + 1));
        log.debug("{} ", stampedReference.getReference());
    }

    @Test
    public void atomicMarkableReference() {
        AtomicMarkableReference<Student> atomicMarkableReference = new AtomicMarkableReference<>(new Student("xiao", 24), true);
        Student student;
        do {
            student = new Student("legal high", 27);
        } while (!atomicMarkableReference.compareAndSet(atomicMarkableReference.getReference(), student, true, false));
        log.debug("{} ", atomicMarkableReference.getReference());
    }

    @Test
    public void atomicIntegerArray() {
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(new int[]{1, 3, 234, 4});
        log.debug("{} ", atomicIntegerArray.incrementAndGet(1));
        atomicIntegerArray.compareAndSet(0, atomicIntegerArray.get(0), 100);
        log.debug("{} ", atomicIntegerArray);
    }


    @Test
    public void atomicReferenceFieldUpdater() throws NoSuchFieldException {
        //不能为非本类字段，不能使用private，必须使用volatile修饰
        AtomicReferenceFieldUpdater<Student, String> re = AtomicReferenceFieldUpdater.newUpdater(Student.class, String.class, "name");
        Student student = new Student("xiao", 23);
        boolean b = re.compareAndSet(student, student.getName(), "新的起点");
        log.debug("{} ", student);
    }

    @Test
    public void atomicIntegerFieldUpdate() {
        //必须是基本数据类型 int
        AtomicIntegerFieldUpdater<Student> fieldUpdater = AtomicIntegerFieldUpdater.newUpdater(Student.class, "age");
        Student xiao = new Student("xiao", 23);
        fieldUpdater.compareAndSet(xiao, xiao.getAge(), 24);
        log.debug("{} ", xiao);
    }

    @Test
    public void atomicLongFieldUpdate() {
        AtomicLongFieldUpdater<Student> fieldUpdater = AtomicLongFieldUpdater.newUpdater(Student.class, "time");
        Student xiao = new Student("xiao", 23);
        fieldUpdater.compareAndSet(xiao,xiao.time,456L);
        log.debug("{} f",xiao);
    }

    @Test
    public void t2() throws NoSuchFieldException {
        Unsafe unsafe = UnsafeUtil.getUnsafe();
        Integer integer = new Integer(100);
        Field value = Integer.class.getDeclaredField("value");
        value.setAccessible(true);
        boolean b = unsafe.compareAndSwapInt(integer, unsafe.objectFieldOffset(value), integer, 2);
        log.debug("{} ", integer);

    }

    private class Student {
        volatile String name;
        volatile int age;
        volatile long time = 123L;

        public Student(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", time=" + time +
                    '}';
        }
    }
}

