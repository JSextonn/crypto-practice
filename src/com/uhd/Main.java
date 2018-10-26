package com.uhd;

public class Main {
    private static final int STRING_COUNT = 100;
    private static final int STRING_LENGTH = 50;
    private static final String TEST_KEY = "Password";
    private static final byte[][] RANDOM_STRINGS = new RandomStringGenerator().generateStringBytes(STRING_COUNT, STRING_LENGTH);

    /**
     * Should not require java 8+
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        CryptoService aesService = new CryptoService("AES", "AES/CBC/PKCS5Padding");
        CryptoService desService = new CryptoService("DES", "DES/CBC/PKCS5Padding");

        // Test aes and des algorithms against the same randomly generated strings.
        EncryptionTestResult aesTestResult = testCryptoService(aesService);
        EncryptionTestResult desTestResult = testCryptoService(desService);

        System.out.println(String.format(
                "Aes: %s seconds, Decrypted Successfully: %s\nDes: %s seconds, Decrypted Successfully: %s",
                aesTestResult.getTimeTookInSeconds(),
                aesTestResult.allDecryptedSuccessfully(),
                desTestResult.getTimeTookInSeconds(),
                desTestResult.allDecryptedSuccessfully()));
    }

    /**
     * Method responsible for running all tests and encapsulating the results.
     * @param service The service that will be used to encrypt and decrypt the random strings.
     * @return The test results
     * @throws Exception
     */
    private static EncryptionTestResult testCryptoService(CryptoService service) throws Exception {
        // Encryption process
        byte[][] ciphers = new byte[STRING_COUNT][];
        long before = System.nanoTime();
        for (int i = 0; i < STRING_COUNT; i++) {
            ciphers[i] = service.encrypt(RANDOM_STRINGS[i], TEST_KEY);
        }
        long nanoes = System.nanoTime() - before;

        // Decryption process
        // Make sure the ciphers can be decrypted back to their original bodies.
        // We could perform the decryption process during the encryption process but this would
        // effect the timing results. We only want to capture the time it takes to encrypt.
        boolean decryptedCorrectly = true;
        for (int i = 0; i < STRING_COUNT; i++) {
            if (!service.decrypt(ciphers[i], TEST_KEY).equals(new String(RANDOM_STRINGS[i]))) {
                decryptedCorrectly = false;
                break;
            }
        }

        return new EncryptionTestResult(nanoes, RANDOM_STRINGS, ciphers, decryptedCorrectly);
    }
}
