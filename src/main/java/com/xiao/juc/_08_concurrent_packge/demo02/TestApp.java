package com.xiao.juc._08_concurrent_packge.demo02;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-17 10:59:27
 * @description
 */
@Slf4j
public class TestApp {
    @Test
    public void t1() {
        log.debug("{}", Integer.MAX_VALUE);
        log.debug("{}",Integer.MIN_VALUE);
        log.debug("{}", ((1 << 29) * 2) * 2 - 1);
        log.debug("{}",8 | 15);
        log.debug("{}",0 & 0);
        log.debug("{}",6 ^ 15);
        log.debug("{}",-1<<29);
        log.debug("{}",-536870912>>29);
        log.debug("{}",~5);
        log.debug("{}",1<<1);

//        -1610612736 -1073741824 -536870912 -2147483648
    }
}
