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

    public String[] getTestBytesAsHex() {
        return this.toHexStrings(this.testBytes);
    }

    public byte[][] getCiphers() {
        return this.ciphers;
    }

    public String[] getCiphersAsHex() {
        return toHexStrings(this.ciphers);
    }

    public String getKey() {
        return this.key;
    }

    public String getKeyAsHex() {
        return toHexString(this.key.getBytes());
    }

    public boolean allDecryptedSuccessfully() {
        return this.decryptedSuccessfully;
    }

    private String toHexString(final byte[] bytes) {
        final char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexCharacters = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            hexCharacters[i * 2] = hexArray[v >>> 4];
            hexCharacters[i * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexCharacters);
    }

    private String[] toHexStrings(final byte[][] bytes) {
        String[] hexArray = new String[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            hexArray[i] = toHexString(bytes[i]);
        }
        return hexArray;
    }
}
