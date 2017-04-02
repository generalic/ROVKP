package hr.fer.tel.rovkp;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    static int counter = 0;

    public static void main(String[] args) throws IOException {
        long t1 = System.nanoTime();

        final Path path = Paths.get("gutenberg/");

        final List<Path> files = new ArrayList<>();

        SimpleFileVisitor<Path> visitor = new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                throws IOException {
                files.add(file);
                counter += Files.readAllLines(file, StandardCharsets.ISO_8859_1).size();
                return FileVisitResult.CONTINUE;
            }
        };
        Files.walkFileTree(path, visitor);

        List<String> lines = files.stream()
            .flatMap(Main::readAllLines)
            .collect(Collectors.toList());

        // TODO: 26/03/17 causes OutOfMemoryError
        //final StringBuilder sb = new StringBuilder();
        //files.parallelStream()
        //    .flatMap(Main::readAllLines)
        //    .forEach(l -> sb.append(l + "\n"));
        //
        //byte[] bytes = sb.toString().getBytes(StandardCharsets.ISO_8859_1);
        //System.out.println(bytes.length);

        Path resultPath = Paths.get("gutenberg_books.txt");
        Files.write(resultPath, lines, StandardCharsets.ISO_8859_1);

        long t2 = System.nanoTime();
        System.out.println(lines.size());

        double result = (t2 - t1) / 1e9;
        System.out.println(result);
    }

    private static Stream<String> readAllLines(Path file) {
        //try {
        //    return Files.readAllLines(file, StandardCharsets.ISO_8859_1).stream();
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
        return null;
    }
}
