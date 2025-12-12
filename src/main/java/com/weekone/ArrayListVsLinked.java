package com.weekone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class ArrayListVsLinked {

    public static void  compare(){
        ArrayList<Integer> arrayList = new ArrayList<>();
        LinkedList<Integer> linkedList = new LinkedList<>();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 10_000_000; i++){
            arrayList.add(i);
        }
        System.out.println(System.currentTimeMillis() - start);

        start = System.currentTimeMillis();
        for (int i = 0; i < 10_000_000; i++){
            linkedList.add(i);
        }
        System.out.println(System.currentTimeMillis() - start);

        System.out.println("find");

        start = System.currentTimeMillis();
        int arrInt = arrayList.get(5_000_000);
        int arrInt2 = arrayList.get(3_000_000);
        System.out.println(System.currentTimeMillis() - start);

        start = System.currentTimeMillis();
        int linkInt = linkedList.get(5_000_000);
        int linkInt2 = linkedList.get(3_000_000);
        System.out.println(System.currentTimeMillis() - start);

        System.out.println("delete");
        start = System.currentTimeMillis();
        arrayList.remove(5_000_00);
        arrayList.remove(2_000_00);
        System.out.println(System.currentTimeMillis() - start);

        start = System.currentTimeMillis();
        linkedList.remove(5_000_00);
        linkedList.remove(2_000_00);
        System.out.println(System.currentTimeMillis() - start);

        HashMap<String, String> map;

    }





}
