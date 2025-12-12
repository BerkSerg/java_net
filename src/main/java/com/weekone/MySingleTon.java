package com.weekone;

public class MySingleTon {

    private MySingleTon(){
        System.out.println("constructor");
    }

    private static class Holder{
        public static final MySingleTon INSTANCE = new MySingleTon();
    }

    public static MySingleTon getInstance(){
        System.out.println("getInstance");
        return Holder.INSTANCE;
    }

}
