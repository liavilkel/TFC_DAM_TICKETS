package com.example.tfc_dam_tickets.utils;

import java.security.SecureRandom;
import java.util.Base64;

public class RandomCodeGenerator {

    private static final int SIZE = 6;

    public static String generateRandomCode() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[SIZE];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
}
