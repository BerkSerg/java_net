package com.weekone;

import java.util.Objects;

public class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // геттеры есть, сеттеров нет

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Person && hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.age);
    }
}
