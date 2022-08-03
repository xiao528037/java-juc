package com.xiao.juc.review._05_pool;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-30 08:41:01
 * @description
 */
@Slf4j(topic = "c.xiao")
public class StringBuilderTest {
    @Test
    public void t1() {


    }

    public static void main(String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("123456789011223344566778899");
        System.out.println(true ? 1 : 2);
    }

    @Test
    public void t2() {
//        int highThree = Integer.MAX_VALUE - 3;
//        Integer i1 = -1;
//        Integer i2 = 0;
//        Integer i3 = 1;
//        Integer i4 = 2;
//        Integer i5 = 3;
//        log.debug("{} {}", -1, Integer.toBinaryString(-1));
//        log.debug("{} {} {}", i1, i1 = i1 << highThree, Integer.toBinaryString(i1));
//        log.debug("{} {} {}", i2, i2 = i2 << highThree, Integer.toBinaryString(i2));
//        log.debug("{} {} {}", i3, i3 = i3 << highThree, Integer.toBinaryString(i3));
//        log.debug("{} {} {}", i4, i4 = i4 << highThree, Integer.toBinaryString(i4));
//        log.debug("{} {} {}", i5, i5 = i5 << highThree, Integer.toBinaryString(i5));

        log.debug("{} ", Integer.toBinaryString(-536870912));
        log.debug("{} ", Integer.toBinaryString(536870911));
        log.debug("{} ", Integer.toBinaryString(-536870902&536870911));
    }
}
