package com.frank.demo.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AES {
    private static final String ALGORITHM = "AES";

    public static String encryptToBase64(String plainText, String key) throws Exception
    {
        byte[] encrypted = encrypt(plainText, key);
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static byte[] encrypt(String plainText, String key) throws Exception
    {
        if (!isKeyValid(key)) {
            throw new Exception("Key must be a string with exactly 16 characters.");
        }

        SecretKeySpec secretKeySpec = getSecretKeySpec(key);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        return cipher.doFinal(plainText.getBytes());
    }

    public static String decryptFromBase64(String cipherText, String key) throws Exception
    {
        byte[] cipherTextByte = Base64.getDecoder().decode(cipherText);
        return decrypt(cipherTextByte, key);
    }

    public static String decrypt(byte[] cipherTextByte, String key) throws Exception
    {
        if (!isKeyValid(key)) {
            throw new Exception("Key must be a string with exactly 16 characters.");
        }

        SecretKeySpec secretKeySpec = getSecretKeySpec(key);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        return new String(cipher.doFinal(cipherTextByte));
    }

    public static String mysql_aes_encrypt(String plainText, String password) throws Exception
    {
        SecretKeySpec secretKey = new SecretKeySpec(password.getBytes(StandardCharsets.UTF_8), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] encrypted =  cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String mysql_aes_decrypt(String cipherText, String password) throws Exception
    {
        SecretKeySpec secretKey = new SecretKeySpec(password.getBytes(StandardCharsets.UTF_8), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] cipherTextByte = Base64.getDecoder().decode(cipherText);
        return new String(cipher.doFinal(cipherTextByte));
    }



    private static SecretKeySpec getSecretKeySpec(String key) {
        return new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);
    }

    private static boolean isKeyValid(String key) {
        return (key != null && key.length() == 16);
    }
}