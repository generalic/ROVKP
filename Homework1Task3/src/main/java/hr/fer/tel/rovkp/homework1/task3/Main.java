package hr.fer.tel.rovkp.homework1.task3;

import java.io.IOException;
import java.net.URISyntaxException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocalFileSystem;
import org.apache.hadoop.fs.Path;

/**
 * Created by generalic on 27/03/17.
 */
public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        Configuration configuration = new Configuration();
        LocalFileSystem localFileSystem = LocalFileSystem.getLocal(configuration);
        FileSystem hdfs = FileSystem.get(configuration);

        Path localFile = new Path("/home/rovkp/test.txt");
        Path hdfsFile = new Path("/user/rovkp/test.txt");

        System.out.println(localFileSystem.isFile(localFile));
        System.out.println(hdfs.isFile(hdfsFile));
    }
}
