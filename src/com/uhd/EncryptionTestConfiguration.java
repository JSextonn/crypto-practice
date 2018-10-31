package com.uhd;

/**
 * Encapsulates all attributes needed to carry out a crypto service test.
 */
public class EncryptionTestConfiguration {
    private int stringCount = 100;
    private int stringLength = 50;
    private String key = "password";
    private CryptoService service;

    public EncryptionTestConfiguration() { }

    public EncryptionTestConfiguration(CryptoService service) {
        this.service = service;
    }

    public int getStringCount() {
        return stringCount;
    }

    public void setStringCount(int stringCount) {
        this.stringCount = stringCount;
    }

    public int getStringLength() {
        return stringLength;
    }

    public void setStringLength(int stringLength) {
        this.stringLength = stringLength;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public CryptoService getService() {
        return service;
    }

    public void setService(CryptoService service) {
        this.service = service;
    }
}
