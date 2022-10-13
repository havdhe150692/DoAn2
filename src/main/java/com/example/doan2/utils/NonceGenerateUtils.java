package com.example.doan2.utils;
import java.security.SecureRandom;

public class NonceGenerateUtils {

    public NonceGenerateUtils(){

    }

    public String newRandom12BytesNonce()
    {
        byte[] nonce = new byte[12];
        new SecureRandom().nextBytes(nonce);
        return convertBytesToHex(nonce);
    }

    // util to print bytes in hex
    private static String convertBytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte temp : bytes) {
            result.append(String.format("%02x", temp));
        }
        return result.toString();
    }

}