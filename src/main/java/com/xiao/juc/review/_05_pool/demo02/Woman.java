package com.xiao.juc.review._05_pool.demo02;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-31 09:35:48
 * @description
 */
public abstract class Woman implements Parent {

    public String name() {
        return getName();
    }

    @Override
    public String toString() {
        return getName() +"  "+ getSex() +"  "+ getSex();
    }
}
