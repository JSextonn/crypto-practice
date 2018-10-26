package com.uhd;

import java.util.Random;
import java.util.function.Supplier;

public class RandomStringSupplier implements Supplier<String> {
    private int stringLength;
    private Random random;

    public RandomStringSupplier(int stringLength) {
        this.stringLength = stringLength;
        this.random = new Random();
    }

    @Override
    public String get() {
        // TODO: Charset should not be hard coded.
        return random.ints(this.stringLength, 32, 127)
                .mapToObj(n -> (char)n)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
