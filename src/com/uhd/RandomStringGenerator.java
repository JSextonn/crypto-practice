package com.uhd;

import java.util.Random;

/**
 * @author Jason Brunelle
 */
public class RandomStringGenerator {
    private Random rand;

    public RandomStringGenerator() {
        this.rand = new Random();
    }

    public byte[][] generateStringBytes(final int count, final int length) {
        byte[][] randomStrings = new byte[count][length];

        for (int i = 0; i < count; i++) {
            for (int j = 0; j < length; j++) {
                randomStrings[i][j] = (byte) (rand.nextInt(96) + 32);
            }
        }

        return randomStrings;
    }
}
