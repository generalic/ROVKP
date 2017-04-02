package hr.fer.tel.rovkp.lab1.task3;

import hr.fer.tel.rovkp.lab1.task3.constants.ValuesGenerator;
import hr.fer.tel.rovkp.lab1.task3.model.Sensor;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.VIntWritable;

public class Main {
    public static void main(String[] args) throws IOException {
        Configuration configuration = new Configuration();
        Path outputPath = new org.apache.hadoop.fs.Path("ocitanja.bin");

        SequenceFile.Writer writer = SequenceFile.createWriter(
            configuration,
            SequenceFile.Writer.file(outputPath),
            SequenceFile.Writer.keyClass(
                VIntWritable.class
            ),
            SequenceFile.Writer.valueClass(
                DoubleWritable.class
            )
        );

        IntStream.rangeClosed(0, 100_000)
            .forEach(i -> {
                try {
                    writer.append(
                        new VIntWritable(ValuesGenerator.getId()),
                        new DoubleWritable(ValuesGenerator.getValue())
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        writer.close();

        SequenceFile.Reader reader = new SequenceFile.Reader(
            configuration,
            SequenceFile.Reader.file(outputPath)
        );

        Observable.create(s -> {
            VIntWritable key = new VIntWritable();
            DoubleWritable val = new DoubleWritable();

            Map<Integer, List<Double>> map = new HashMap<>();

            while (reader.next(key, val)) {
                Sensor sensor = new Sensor(key.get(), val.get());
                s.onNext(sensor);
            }
            reader.close();
            s.onComplete();
        });

        VIntWritable key = new VIntWritable();
        DoubleWritable val = new DoubleWritable();

        Map<Integer, List<Double>> map = new HashMap<>();

        while (reader.next(key, val)) {
            int k = key.get();
            double v = val.get();

            List<Double> values = map.get(k);
            if (Objects.isNull(values)) {
                values = new ArrayList<>();
            }
            values.add(v);

            map.put(k, values);
        }
        reader.close();

        map.entrySet().stream()
            .map(e -> {
                List<Double> values = e.getValue();
                double average = values.stream().mapToDouble(x -> x).average().getAsDouble();
                return new Sensor(e.getKey(), average);
            })
            .sorted()
            .forEach(System.out::println);
    }
}
