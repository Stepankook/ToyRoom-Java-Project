package com.toyroom.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class FileLoader {

    private static final String FILE_PATH = "init.txt";

    public static Map<String, String> loadInitParams() {
        Map<String, String> params = new HashMap<>();
        try (Stream<String> lines = Files.lines(Paths.get(FILE_PATH))) {
            lines.filter(line -> line.contains("="))
                    .forEach(line -> {
                        String[] parts = line.split("=", 2);
                        if (parts.length == 2) {
                            params.put(parts[0].trim(), parts[1].trim());
                        }
                    });
        } catch (IOException e) {
            System.err.println("Помилка завантаження файлу ініціалізації: " + e.getMessage());
        }
        return params;
    }
}