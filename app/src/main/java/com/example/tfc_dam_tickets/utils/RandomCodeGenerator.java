package com.example.tfc_dam_tickets.utils;

import java.security.SecureRandom;
import java.util.Base64;

public class RandomCodeGenerator {

    public static String generateRandomCode() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[6];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
}
