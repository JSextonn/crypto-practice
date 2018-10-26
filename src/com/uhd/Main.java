package com.uhd;

import java.util.Random;

public class Main {
    private static final String PLAIN = "This is the plain text";
    private static final String KEY = "Password";

    public static void main(String[] args) throws Exception {
        Random rand = new Random();
        byte [][] randomStrings = new byte[100][50];
        long before, aesMillis, desMillis;

        for (int i = 0; i < 100; i++)
            for (int j = 0; j < 50; j++)
                randomStrings[i][j] = (byte)(rand.nextInt(96) + 32);
        for (int i = 0; i < 100; i++)
            System.out.println(new String(randomStrings[i]));
        // Aes encryption examples
        CryptoService aesService = new CryptoService("AES", "AES/CBC/PKCS5Padding");
        CryptoService desService = new CryptoService("DES", "DES/CBC/PKCS5Padding");

        before = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            byte[] aesCipher = aesService.encrypt(randomStrings[i], KEY);
        }
        aesMillis = System.currentTimeMillis() - before;

        //System.out.println(aesService.decrypt(aesCipher, KEY));

        // Des encryption examples
        before = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            byte[] desCipher = desService.encrypt(randomStrings[i], KEY);
        }
        desMillis = System.currentTimeMillis() - before;

        System.out.println("AES " + aesMillis + ", DES " + desMillis);
        //System.out.println(new String(desCipher));
        //System.out.println(desService.decrypt(desCipher, KEY));
    }
}
