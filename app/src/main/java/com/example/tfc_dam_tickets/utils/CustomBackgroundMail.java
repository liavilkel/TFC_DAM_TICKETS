package com.example.tfc_dam_tickets.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;
import com.creativityapps.gmailbackgroundlibrary.util.Utils;

public class CustomBackgroundMail extends BackgroundMail {

    public CustomBackgroundMail(Context context) {
        super(context);
    }

    @Override
    public void send() {
        Log.e("USING", "OVERRIDE");
        new CustomSendEmailTask().execute();
    }

    private class CustomSendEmailTask extends BackgroundMail.SendEmailTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Remove code responsible for showing progress dialog
        }

        @Override
        protected void onPostExecute(Boolean result) {
            //super.onPostExecute(result);
            // Remove code responsible for showing toasts
        }
    }
}

