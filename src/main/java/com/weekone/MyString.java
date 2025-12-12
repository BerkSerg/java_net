package com.weekone;

import java.util.Objects;

public class MyString{
    String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyString myString = (MyString) o;
        return Objects.equals(name, myString.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
