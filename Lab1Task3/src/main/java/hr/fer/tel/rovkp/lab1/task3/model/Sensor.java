package hr.fer.tel.rovkp.lab1.task3.model;

/**
 * Created by generalic on 30/03/17.
 */
public class Sensor implements Comparable<Sensor> {
    private final int id;
    private final double value;

    public Sensor(int id, double value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Senzor " + id + ": " + value;
    }

    @Override
    public int compareTo(Sensor o) {
        return Integer.compare(id, o.getId());
    }
}
