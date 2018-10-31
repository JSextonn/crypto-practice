package com.uhd;

public class Main {
    /**
     * Should not require java 8+
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // Responsible for carrying out specific tests on a specified crypto service.
        final EncryptionTestRunner testRunner = new EncryptionTestRunner();

        // Perform AES test
        CryptoService aesService = new CryptoService("AES", "AES/CBC/PKCS5Padding");
        testRunner.getTestConfiguration().setService(aesService);
        EncryptionTestResult aesTestResult = testRunner.testCryptoService();
        System.out.println("AES Result");
        displayResult(aesTestResult);

        // Perform DES test
        CryptoService desService = new CryptoService("DES", "DES/CBC/PKCS5Padding");
        testRunner.getTestConfiguration().setService(desService);
        EncryptionTestResult desTestResult = testRunner.testCryptoService();
        System.out.println("DES Result");
        displayResult(desTestResult);
    }

    /**
     * Displays the content of a EncryptionTestResult as clearly as possible
     *
     * @param result The EncryptionTestResult contents that will be displayed.
     */
    private static void displayResult(EncryptionTestResult result) {
        System.out.println(String.format(
                "%s seconds, Decrypted Successfully: %s",
                result.getTimeTookInSeconds(),
                result.allDecryptedSuccessfully()));

        System.out.println(String.format("Key used: %s", Convert.toHexString(result.getKey().getBytes())));

        System.out.println("Plain Hexes");
        System.out.println(String.join("\n", Convert.toHexStrings(result.getTestBytes())));

        System.out.println("Cipher Hexes");
        System.out.println(String.join("\n", Convert.toHexStrings(result.getCiphers())));
    }
}
