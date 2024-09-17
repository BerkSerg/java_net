package com.top.simplerest;

public class TwoNumbers {
    int numa;
    int numb;

    public TwoNumbers(int numa, int numb) {
        this.numa = numa;
        this.numb = numb;
    }

    public int getSumm() {
        return numa + numb;
    }
}