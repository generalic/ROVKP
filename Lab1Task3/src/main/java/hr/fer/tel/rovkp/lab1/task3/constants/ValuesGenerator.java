package hr.fer.tel.rovkp.lab1.task3.constants;

import hr.fer.tel.rovkp.lab1.task3.util.RNGRandomImpl;

/**
 * Created by generalic on 27/02/17.
 */

public final class ValuesGenerator {

    private static final int SENSOR_ID_MIN = 1;
    private static final int SENSOR_ID_MAX = 100;

    private static final double SENSOR_VALUE_MIN = 0.00;
    private static final double SENSOR_VALUE_MAX = 99.99;

    private static final RNGRandomImpl random = new RNGRandomImpl();

    public static int getId() {
        return random.nextInt(SENSOR_ID_MIN, SENSOR_ID_MAX);
    }

    public static double getValue() {
        return random.nextDouble(SENSOR_VALUE_MIN, SENSOR_VALUE_MAX);
    }
}
