package com.weekone;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConfigValidator {

    public static void main(String[] args){
        final List<String> invalidLines = new ArrayList<>();
        final String file = "config.txt";

        try {
            List<String> lines = Files.readAllLines(Path.of(file));
            Map<String, String> configResult = lines.stream()
                    .filter(l -> {
                        if(!isValidLine(l)){
                            invalidLines.add(l);
                            return false;
                        }
                        return true;
                    })
                    .map(l -> l.split("="))
                    .collect(Collectors.toMap(
                            arr -> arr[0],
                            arr -> arr[1],
                            (value1, value2) -> {
                                System.out.println("Duplicate key with param " + value1);

                                return value2;
                            }
                    ));

            printReport(configResult, invalidLines);

        }catch(IOException e){
            throw new RuntimeException("Ошибка обработки файла " + file, e);
        }
    }

    private static void printReport(Map<String, String> configResult, List<String> invalidLines){
        System.out.printf("Общее количество строк: %d%n", configResult.size() + invalidLines.size());
        System.out.printf("Корректных строк: %d%n", configResult.size());
        System.out.printf("Некорректных строк: %d%n", invalidLines.size());
        if (invalidLines.size() > 0){
            System.out.println("Найдены некорректные строки:");
            for (int i = 1; i <= invalidLines.size(); i++){
                System.out.printf("[%d] %s%n", i, invalidLines.get(i - 1));
            }
        }
        System.out.println("Содержание конфигурации:");
        for(Map.Entry entry : configResult.entrySet()){
            System.out.printf("""
                Ключ: "%s" -> Значение: "%s"
                """, entry.getKey(), entry.getValue());
        }
    }

    public static boolean isValidLine(String line){
        if (line.isBlank()){
            return false;
        }
        int delimPos = line.indexOf("=");
        if (delimPos != line.lastIndexOf("=")){
            return false;
        }
        if (delimPos == 0 || delimPos == line.length() - 1){
            return false;
        }
        return true;
    }
}
