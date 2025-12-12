package com.weekone;

import java.util.Optional;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main4 {

    public static void main(String[] args) {

        String message = "asdasdasd";

        System.out.println(message.replaceAll(".", "*"));
        StringBuilder result = new StringBuilder();

        int n = 5;


        String[] inputValues = readInput().orElseThrow();
        System.out.println(inputValues);
        String[] inputValues1 = readInput().orElseThrow();
        System.out.println(inputValues1);
        int x = Integer.parseInt(inputValues[0]);
        int y = Integer.parseInt(inputValues[1]);
        String direction = inputValues[2];


        switch (direction) {
            case "up" -> y = (y > 0) ? y = 1 : 0;
            case "down" -> y = (y < 100) ? y + 1 : 100;
            case "left" -> x = (x > 0) ? x - 1 : 0;
            case "right" -> x = (x < 100) ? x + 1 : 100;
            default -> throw new IllegalArgumentException("Wrong direction");
        }

        System.out.printf("x: %d, y: %d, direction: %s", x, y, direction);
    }

    public static Optional<String[]> readInput() {
        String[] inputValues = null;

        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            inputValues = input.split(" ");
        }
        //scanner.close();

        return Optional.ofNullable(inputValues);
    }
}

