package com.weekone;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.http.HttpClient;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Main3 {
    public static void main(String[] args){

        Pattern p = Pattern.compile("\\d");
        Matcher m = p.matcher(" -041");
        m.find();
        System.out.println(m.group());

        Stream<Integer> st = Stream.of(1,2,3,4).map(x->x*5);


        new ArrayList<>(List.of("Pushover", "Novice", "Fighter", "Warrior", "Veteran", "Sage", "Elite", "Conqueror", "Champion", "Master", "Greatest"));

        int[] arr1 = {1, 2};
        int[] arr2 = new int[]{1,2};
        //arr1.clone()

        Stream.generate(() -> "s1").limit(12).forEach(System.out::println);

        Stream.iterate(40, n -> n * 2).limit(20).forEach(System.out::println);


        Path path = Paths.get("d:\\TEMP_STD\\BS_A_CustomerInfo (1).xml");
        try {
            Stream<String> lines = Files.lines(path);
            lines.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ServerSocket serverSocket = new ServerSocket(8080);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
