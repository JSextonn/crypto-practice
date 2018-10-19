package com.uhd;

public class Main {
    private static final String PLAIN = "This is the plain text";
    private static final String KEY = "Password";

    public static void main(String[] args) throws Exception {
        // Aes encryption examples
        CryptoService aesService = new CryptoService("AES", "AES/CBC/PKCS5Padding");
        byte[] aesCipher = aesService.encrypt(PLAIN, KEY);
        System.out.println(new String(aesCipher));
        System.out.println(aesService.decrypt(aesCipher, KEY));

        // Des encryption examples
        CryptoService desService = new CryptoService("DES", "DES/CBC/PKCS5Padding");
        byte[] desCipher = desService.encrypt(PLAIN, KEY);
        System.out.println(new String(desCipher));
        System.out.println(desService.decrypt(desCipher, KEY));
    }
}
