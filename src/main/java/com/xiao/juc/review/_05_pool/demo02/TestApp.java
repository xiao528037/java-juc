package com.xiao.juc.review._05_pool.demo02;

import lombok.extern.slf4j.Slf4j;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-31 09:53:51
 * @description
 */
@Slf4j(topic = "person")
public class TestApp {
    public static void main(String[] args) {
        XiaoPerson xiaoPerson = new XiaoPerson();
        log.debug("{} ",xiaoPerson);
    }
}
