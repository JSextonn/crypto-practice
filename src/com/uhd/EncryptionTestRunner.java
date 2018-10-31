package com.uhd;

public class EncryptionTestRunner {
    private EncryptionTestConfiguration config;
    private byte[][] generatedBytes;

    public EncryptionTestRunner() {
        this(new EncryptionTestConfiguration());
    }

    public EncryptionTestRunner(CryptoService service) {
        this(new EncryptionTestConfiguration(service));
    }

    public EncryptionTestRunner(EncryptionTestConfiguration config) {
        this.config = config;
        this.populateTestBytes();
    }

    /**
     * Carries out the testing action with the given test configuration and returns the
     * result in a single object
     *
     * @return The result of the test
     * @throws Exception
     */
    public EncryptionTestResult testCryptoService() throws Exception {
        // A crypto service must be specified before testing.
        // All other attributes have defaults
        if (this.config.getService() == null) {
            throw new UnsupportedOperationException("A crypto service much be provided before testing");
        }

        // Encryption process
        byte[][] ciphers = new byte[this.config.getStringCount()][];
        long before = System.nanoTime();
        for (int i = 0; i < this.config.getStringCount(); i++) {
            ciphers[i] = this.config.getService().encrypt(this.generatedBytes[i], this.config.getKey());
        }
        long nanoes = System.nanoTime() - before;

        // Decryption process
        // Make sure the ciphers can be decrypted back to their original bodies.
        // We could perform the decryption process during the encryption process but this would
        // effect the timing results. We only want to capture the time it takes to encrypt.
        boolean decryptedCorrectly = true;
        for (int i = 0; i < this.config.getStringCount(); i++) {
            if (!this.config.getService().decrypt(ciphers[i], this.config.getKey())
                    .equals(new String(this.generatedBytes[i]))) {
                decryptedCorrectly = false;
                break;
            }
        }

        return new EncryptionTestResult(nanoes, this.generatedBytes, ciphers, this.config.getKey(), decryptedCorrectly);
    }

    /**
     * Randomly generates strings that will be encrypted and decrypted during testing
     */
    public void populateTestBytes() {
        this.generatedBytes = new RandomStringGenerator()
                .generateStringBytes(config.getStringCount(), config.getStringLength());
    }

    public EncryptionTestConfiguration getTestConfiguration() {
        return this.config;
    }
}
