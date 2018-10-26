package com.uhd;

public class EncryptionTestResult {
    private long timeTook;
    private byte[][] plaintext;
    private byte[][] ciphers;
    private boolean decryptedSuccessfully;

    public EncryptionTestResult(long timeTook, byte[][] plaintext, byte[][] ciphers, boolean decryptSuccessfully) {
        this.timeTook = timeTook;
        this.plaintext = plaintext;
        this.ciphers = ciphers;
        this.decryptedSuccessfully = decryptSuccessfully;
    }

    public long getTimeTookInNanos() {
        return this.timeTook;
    }

    public double getTimeTookInSeconds() {
        return (double) this.timeTook / 1_000_000_000.0;
    }

    public byte[][] getOriginalPlainTextBytes() {
        return this.plaintext;
    }

    public byte[][] getCiphers() {
        return this.ciphers;
    }

    public boolean allDecryptedSuccessfully() {
        return this.decryptedSuccessfully;
    }
}
