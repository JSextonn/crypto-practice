package com.uhd;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Jason Brunelle
 */
public class RandomStringGenerator {
    public RandomStringGenerator() {
    }

    public byte[][] generateStringBytes(final int count, final int length) {
        byte[][] randomStrings = new byte[count][length];

        for (int i = 0; i < count; i++) {
            for (int j = 0; j < length; j++) {
                randomStrings[i][j] = (byte) ThreadLocalRandom.current().nextInt(32, 126 + 1);
            }
        }

        return randomStrings;
    }
}
