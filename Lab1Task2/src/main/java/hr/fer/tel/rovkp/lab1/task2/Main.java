package hr.fer.tel.rovkp.lab1.task2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocalFileSystem;

public class Main {

    public static void main(String[] args) throws IOException {
        long t1 = System.nanoTime();

        Configuration configuration = new Configuration();
        LocalFileSystem localFileSystem = LocalFileSystem.getLocal(configuration);
        FileSystem hdfs = FileSystem.get(configuration);

        Path path = Paths.get("gutenberg");

        org.apache.hadoop.fs.Path hdfsFile =
            new org.apache.hadoop.fs.Path("/user/rovkp/bgeneralic/gutenberg_books.txt");

        BufferedWriter writer = new BufferedWriter(
            new OutputStreamWriter(
                hdfs.create(hdfsFile)
            )
        );

        SimpleFileVisitor<Path> visitor = new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                throws IOException {
                // TODO: 30/03/17 read file

                BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(
                        Files.newInputStream(file),
                        StandardCharsets.ISO_8859_1
                    )
                );
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    writer.write(line);
                    writer.flush();
                }
                bufferedReader.close();

                return FileVisitResult.CONTINUE;
            }
        };
        Files.walkFileTree(path, visitor);
        writer.close();


        long t2 = System.nanoTime();
        double result = (t2 - t1) / 1e9;
        System.out.println(result);
    }
}
