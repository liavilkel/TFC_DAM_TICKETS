package com.example.tfc_dam_tickets.utils;

public class SessionManager {
    private static String loggedInUserEmail;

    public static void setLoggedInUserEmail(String email) {
        loggedInUserEmail = email;
    }

    public static String getLoggedInUserEmail() {
        return loggedInUserEmail;
    }
}

