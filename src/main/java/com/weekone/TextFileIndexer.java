package com.weekone;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class TextFileIndexer {

    private static final Map<String, Set<String>> searchIndexes = new HashMap<>();

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        final String folder = "Books" ;

        if (!Files.exists(Path.of(folder))){
            throw new RuntimeException("Директория не существует");
        }

        try {
            Files.walk(Path.of(folder))
                    .filter(path -> path.getFileName().toString().toLowerCase().endsWith(".txt"))
                    .forEach(TextFileIndexer::indexFile);

            System.out.println("Индекс построен. Введите слово для поиска (или :quit для выхода):");
            String word;
            while (scanner.hasNextLine()){
                word = scanner.nextLine().trim().toLowerCase();

                if (word.isEmpty()){
                    continue;
                }

                if (word.equals(":quit")){
                    return;
                }

                Set<String> value = searchIndexes.get(word);
                if (value == null){
                    System.out.printf("Слово %s не содержится ни в одном файле%n", word);
                }else{
                    System.out.println("Слово содержится в следующих файлах:");
                    value.forEach(System.out::println);
                }
            }
        }catch(IOException e){
            throw new RuntimeException("Error process folder", e);
        }
    }

    private static void indexFile(Path path){
        String filename = path.getFileName().toString();
        try {
            Files.readAllLines(path, StandardCharsets.UTF_8).stream()
                    .flatMap(TextUtil::simplifyText)
                    .forEach(word -> {
                        searchIndexes.computeIfAbsent(word, k -> new HashSet<>())
                                .add(filename);
                    });
        } catch (IOException e) {
            throw new RuntimeException("Ошибка обработки файла " + filename, e);
        }
    }

    private static class TextUtil{
        private static final Pattern PATTERN = Pattern.compile("\\p{L}+");

        public static Stream<String> simplifyText(String text){
            return PATTERN.matcher(text).results()
                    .map(MatchResult::group)
                    .filter(s -> !s.isEmpty())
                    .map(String::toLowerCase);
        }
    }
}
