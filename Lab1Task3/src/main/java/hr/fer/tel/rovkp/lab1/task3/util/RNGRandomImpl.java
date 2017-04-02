package hr.fer.tel.rovkp.lab1.task3.util;

import java.util.Random;

public class RNGRandomImpl {

    private Random rand = RandomProvider.get();

    public double nextDouble() {
        return rand.nextDouble();
    }

    public double nextDouble(double min, double max) {
        return (max - min) * rand.nextDouble() + min;
    }

    public float nextFloat() {
        return rand.nextFloat();
    }

    public float nextFloat(float min, float max) {
        return (max - min) * rand.nextFloat() + min;
    }

    public int nextInt() {
        return rand.nextInt();
    }

    public int nextInt(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }

    public boolean nextBoolean() {
        return rand.nextBoolean();
    }

    public double nextGaussian() {
        return rand.nextGaussian();
    }
}
