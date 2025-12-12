package com.weekone;

public class Counter extends Thread{
    private int count;

    public Counter(){
        this.count = 0;
    }

    private void increment(){
        System.out.println(this.count++);
        try{
            Thread.sleep(100);
        }catch(Exception e){}
        increment();
    }

    @Override
    public void run() {
        increment();
    }
}
