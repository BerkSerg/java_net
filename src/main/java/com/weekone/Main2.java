package com.weekone;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main2 {
    public static void main(String[] args) {

        var s = "string";
        var s1 = "${s}";
        System.out.println(s1);

        System.out.println(f(3));
        System.out.println(f(4));
        System.out.println(f(5));
        System.out.println(f(6));

        System.out.println(Arrays.toString(getArrayOfPow(new int[]{1,2,3,4})));
    }

    static int f(int n){
        if (n == 1) return 1;
        if (n == 2) return 2;

        return f(n - 1) + f(n - 2);
    }

    private String[] getUserNames (User[] users){
        var arr = List.of(users);
        return arr.stream()
            .map(User::getEmail)
            .toArray(String[]::new);
    }

    private static int[] getArrayOfPow(int... nums){
        int pow = Arrays.stream(nums)
                .reduce(1, (a, b) -> a * b);

        return Arrays.stream(nums)
                .map(n -> pow - n)
                .toArray();
    }

    private static String[] toUpperFirst(String[] words){
        return Arrays.stream(words)
                .map( w -> w.substring(0,1).toUpperCase() + w.substring(1)
                ).toArray(String[]::new);
    }


}
