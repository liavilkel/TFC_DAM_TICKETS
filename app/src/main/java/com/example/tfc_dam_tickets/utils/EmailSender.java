package com.example.tfc_dam_tickets.utils;

import android.content.Context;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;

public class EmailSender {
    public static void sendEmail(Context context, String toEmail, String subject, String body) {

        String signature = "\n\n\n\n--\nSaludos cordiales,\nEquipo de Soporte\nResolveRocket\nCorreo: " +
                "resolverocket2024@gmail.com\nTeléfono: +34 123 456 789\nSitio web: www.resolverocket.com";

        BackgroundMail.newBuilder(context)
                .withUsername(SenderEmailInfo.EMAIL)
                .withPassword(SenderEmailInfo.PASSWORD)
                .withMailto(toEmail)
                .withType(BackgroundMail.TYPE_PLAIN)
                .withSubject(subject)
                .withBody(body + signature)
                .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                    @Override
                    public void onSuccess() {
                        //
                    }
                })
                .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                    @Override
                    public void onFail() {
                        //
                    }
                })
                .send();
    }
}
