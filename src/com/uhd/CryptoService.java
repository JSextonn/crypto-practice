package com.uhd;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;


/**
 * Notes:
 * Crypto service has only been tested with AES/CBC and DES/CBC (poorly)
 * Crypto service will not work with ECB.
 */
public class CryptoService {
    private final String HASH_ALGORITHM;
    private final String ALGORITHM;
    private final Cipher CIPHER;
    private final int BLOCK_SIZE;

    // TODO: Extract algorithm from mode using regex. This will allow for only a mode to be passed in.
    // TODO: Potentially add salting functionality
    public CryptoService(final String algorithm, final String mode) throws Exception {
        this.ALGORITHM = algorithm;
        this.CIPHER = Cipher.getInstance(mode);
        this.BLOCK_SIZE = CIPHER.getBlockSize();
        this.HASH_ALGORITHM = "SHA-256";
    }

    /**
     * Generates an IV that can be used with the current mode of operation
     * @return The generated IV
     */
    private IvParameterSpec generateIv() {
        byte[] iv = new byte[BLOCK_SIZE];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    /**
     * Computes the secret key of n length depending of the mode of operation.
     * @param key The key that will "seed" the
     * @return The computed secret key that can be used with the current mode of operation
     */
    private SecretKeySpec computeSecretKey(String key) throws Exception {
        MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
        digest.update(key.getBytes(StandardCharsets.UTF_8));
        byte[] keyBytes = new byte[BLOCK_SIZE];
        System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);
        return new SecretKeySpec(keyBytes, ALGORITHM);
    }

    /**
     * Encrypts given plaintext string with given key
     * @param plainText The plaintext to be encrypted
     * @param key The key that will be used to encrypt the plaintext
     * @return The encrypted data
     * @throws Exception
     */
    public byte[] encrypt(String plainText, String key) throws Exception {
        return encrypt(plainText.getBytes(), key);
    }

    /**
     * Encrypts given plaintext character with given key
     * @param plainText The plaintext characters to be encrypted
     * @param key The key that will be used to encrypt the plaintext
     * @return The encrypted data
     * @throws Exception
     */
    public byte[] encrypt(char[] plainText, String key) throws Exception {
        return encrypt(new String(plainText).getBytes(), key);
    }

    /**
     * Encrypts plain text into cipher text
     * @param plainText The data that should be encrypted
     * @param key       The key the data should be encrypted with
     * @return The encrypted bytes
     */
    public byte[] encrypt(byte[] plainText, String key) throws Exception {
        // Generate iv and encrypt with given key
        IvParameterSpec ivParameterSpec = generateIv();
        SecretKeySpec secretKeySpec = computeSecretKey(key);
        CIPHER.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] encrypted = CIPHER.doFinal(plainText);

        // Combine IV and encrypted cipher text
        // Decryption wont work without same IV
        byte[] encryptedIVAndText = new byte[BLOCK_SIZE + encrypted.length];
        System.arraycopy(ivParameterSpec.getIV(), 0, encryptedIVAndText, 0, BLOCK_SIZE);
        System.arraycopy(encrypted, 0, encryptedIVAndText, BLOCK_SIZE, encrypted.length);

        return encryptedIVAndText;
    }

    /**
     * Extracts the leading IV from encrypted bytes
     * @param encryptedBytes The bytes the IV should be extracted from
     * @return The leading IV from the encrypted bytes
     */
    private IvParameterSpec extractIv(byte[] encryptedBytes) {
        byte[] iv = new byte[BLOCK_SIZE];
        System.arraycopy(encryptedBytes, 0, iv, 0, iv.length);
        return new IvParameterSpec(iv);
    }

    /**
     * Extracts the encrypted bytes from cipher text, ignore the concatenated IV
     * in the first n bytes
     * @param encryptedText The encrypted that should be separated from the IV.
     * @return The cipher text separate from the IV.
     */
    private byte[] extractEncryptedBytes(byte[] encryptedText) {
        int encryptedSize = encryptedText.length - BLOCK_SIZE;
        byte[] encryptedBytes = new byte[encryptedSize];
        System.arraycopy(encryptedText, BLOCK_SIZE, encryptedBytes, 0, encryptedSize);
        return encryptedBytes;
    }

    /**
     * Decrypt encrypted data
     * @param encryptedIvTextBytes The encrypted bytes
     * @param key                  The key used to encrypt the plain bytes
     * @return The decrypted bytes
     */
    public String decrypt(byte[] encryptedIvTextBytes, String key) throws Exception {
        // Extract IV and initialize key
        IvParameterSpec ivParameterSpec = extractIv(encryptedIvTextBytes);
        byte[] encryptedBytes = extractEncryptedBytes(encryptedIvTextBytes);
        SecretKeySpec secretKeySpec = computeSecretKey(key);

        // Decrypt data with extracted information and given key.
        CIPHER.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] decrypted = CIPHER.doFinal(encryptedBytes);
        return new String(decrypted);
    }
}
