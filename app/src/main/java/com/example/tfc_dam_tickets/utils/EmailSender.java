package com.example.tfc_dam_tickets.utils;

import android.content.Context;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;
import com.example.tfc_dam_tickets.R;

public class EmailSender {
    public static void sendEmail(Context context, String toEmail, String subject, String body) {

        //BackgroundMail.newBuilder(context)
        CustomBackgroundMail.newBuilder(context)
                .withUsername(SenderEmailInfo.EMAIL)
                .withPassword(SenderEmailInfo.PASSWORD)
                .withMailto(toEmail)
                .withType(BackgroundMail.TYPE_PLAIN)
                .withSubject(subject)
                .withBody(body + R.string.signature)
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
