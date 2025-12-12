package com.weekone;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main5 {

    public static void main(String[] args){
        File file = new File("d:\\Мое обучение\\FinTrack.txt");

        "dfsdf".strip().trim();


        try {
            Scanner scanner = new Scanner(file);
            String line = scanner.nextLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
