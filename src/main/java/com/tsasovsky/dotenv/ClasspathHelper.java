package com.tsasovsky.dotenv;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Classpath helper
 */
public class ClasspathHelper {
    static Stream<String> loadFileFromClasspath(String location) {
        final ClassLoader loader = ClasspathHelper.class.getClassLoader();
        assert loader != null;
        InputStream inputStream = loader.getResourceAsStream(location);
        if (inputStream == null) {
            inputStream = loader.getResourceAsStream(location);
        }
        if (inputStream == null) {
            inputStream = ClassLoader.getSystemResourceAsStream(location);
        }

        if (inputStream == null) {
            throw new DotenvException("Could not find " + location + " on the classpath");
        }

        final Scanner scanner = new Scanner(inputStream, String.valueOf(StandardCharsets.UTF_8));
        final List<String> lines = new ArrayList<>();
        while (scanner.hasNext()) {
            lines.add(scanner.nextLine());
        }

        return lines.stream();
    }
}
