package com.xiao.juc._08_concurrent_packge.demo03;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-17 21:18:58
 * @description
 */
@Slf4j(topic = "c.testApp")
public class TestApp {

    @Test
    public void t1() {
        //直接堆栈溢出
        for (int i = 0; i < 10; i++) {
            Long begin = System.currentTimeMillis();
            MyTask myTask = new MyTask(10000);
            log.debug("结果 {} 耗时 {}", myTask.compute(), System.currentTimeMillis() - begin);
        }
    }

    @Test
    public void t2() {
        for (int i = 0; i < 10; i++) {
            Long begin = System.currentTimeMillis();
            MyTaskTwo myTaskTwo = new MyTaskTwo(1, 10000);
            log.debug("结果 {} -- 耗时 {}", myTaskTwo.compute(), System.currentTimeMillis() - begin);
        }

    }

    @Test
    public void t3() {
        //通过分治的方式计算1-10000的值
        for (int i = 0; i < 10; i++) {
            Long begin = System.currentTimeMillis();
            int sum = sum(1, 10000);
            log.debug("结果 {} -- 耗时 {}", sum, System.currentTimeMillis() - begin);
        }
    }

    public int sum(int begin, int end) {
        if (begin == end) {
            return end;
        }
        if (begin - end == 1) {
            return begin + end;
        }
        int half = (begin + end) / 2;
        return sum(begin, half) + sum(half + 1, end);
    }


    public int[] runningSum(int[] nums) {

        for (int i = 1; i < nums.length; i++) {
            nums[i] += nums[i - 1];
        }
        return nums;
    }

    @Test
    public void t11() {
        //"i love eating burger"
        //"burg"
//        "hello from the other side"
//        "they"
        int prefixOfWord = isPrefixOfWord("12 123456 124 2 123", "123");
        System.out.println(prefixOfWord);
    }

    public int isPrefixOfWord(String sentence, String searchWord) {
        String[] sentenceStrArray = sentence.split(" ");
        char[] wordChar = searchWord.toCharArray();
        for (int i = 0; i < sentenceStrArray.length; i++)  {
            char[] chars = sentenceStrArray[i].toCharArray();
            if (chars.length < wordChar.length || chars[0] != wordChar[0]) {
                continue;
            }
            for (int j = 0; j < wordChar.length; j++) {
                if (chars[j] == wordChar[j]) {
                    if (j == wordChar.length - 1) {
                        return i + 1;
                    }
                    continue;
                }
                break;
            }
        }
        return -1;
    }
}
