package com.uhd;

public class EncryptionTestResult {
    private final long timeTook;
    private final byte[][] testBytes;
    private final byte[][] ciphers;
    private final String key;
    private final boolean decryptedSuccessfully;

    public EncryptionTestResult(
            long timeTook,
            byte[][] testBytes,
            byte[][] ciphers,
            String key,
            boolean decryptSuccessfully) {
        this.timeTook = timeTook;
        this.testBytes = testBytes;
        this.ciphers = ciphers;
        this.key = key;
        this.decryptedSuccessfully = decryptSuccessfully;
    }

    public long getTimeTookInNanos() {
        return this.timeTook;
    }

    public double getTimeTookInSeconds() {
        return (double) this.timeTook / 1_000_000_000.0;
    }

    public byte[][] getTestBytes() {
        return this.testBytes;
    }

    public byte[][] getCiphers() {
        return this.ciphers;
    }

    public String getKey() {
        return this.key;
    }

    public boolean allDecryptedSuccessfully() {
        return this.decryptedSuccessfully;
    }
}
