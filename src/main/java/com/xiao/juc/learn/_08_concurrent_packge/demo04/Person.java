package com.xiao.juc.learn._08_concurrent_packge.demo04;

import java.util.Objects;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-18 17:26:44
 * @description
 */

public class Person {
    private String name;
    private Integer age;

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return name.equals(person.name) && age.equals(person.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
