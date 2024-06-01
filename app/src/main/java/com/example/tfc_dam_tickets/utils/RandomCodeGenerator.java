package com.example.tfc_dam_tickets.utils;

import java.security.SecureRandom;
import java.util.Base64;

public class RandomCodeGenerator {

    public static String generateRandomCode(int length) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[length];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
}
