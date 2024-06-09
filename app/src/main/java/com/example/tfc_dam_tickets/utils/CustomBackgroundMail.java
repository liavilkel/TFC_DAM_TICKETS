package com.example.tfc_dam_tickets.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.creativityapps.gmailbackgroundlibrary.util.GmailSender;
import com.creativityapps.gmailbackgroundlibrary.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class CustomBackgroundMail {
    String TAG = "CustomBackgroundMail";
    private String username;
    private String password;
    private String mailto;
    private String subject;
    private String body;
    private String type;
    private boolean processVisibility = true;
    private ArrayList<String> attachments = new ArrayList<>();
    private Context mContext;
    private OnSuccessCallback onSuccessCallback;
    private OnFailCallback onFailCallback;

    public final static String TYPE_PLAIN = "text/plain";
    public final static String TYPE_HTML = "text/html";

    public interface OnSuccessCallback {
        void onSuccess();
    }

    public interface OnFailCallback {
        void onFail();
    }

    public CustomBackgroundMail(Context context) {
        this.mContext = context;
    }

    private CustomBackgroundMail(Builder builder) {
        mContext = builder.context;
        attachments = builder.attachments;
        username = builder.username;
        password = builder.password;
        mailto = builder.mailto;
        subject = builder.subject;
        body = builder.body;
        type = builder.type;
        processVisibility = builder.processVisibility;
        setOnSuccessCallback(builder.onSuccessCallback);
        setOnFailCallback(builder.onFailCallback);
    }

    public static Builder newBuilder(Context context) {
        return new Builder(context);
    }

    public void setGmailUserName(@NonNull String string) {
        this.username = string;
    }

    public void setGmailPassword(@NonNull String string) {
        this.password = string;
    }

    public void setType(@NonNull String string) {
        this.type = string;
    }

    public void setMailTo(@NonNull String string) {
        this.mailto = string;
    }

    public void setFormSubject(@NonNull String string) {
        this.subject = string;
    }

    public void setFormBody(@NonNull String string) {
        this.body = string;
    }

    public void addAttachment(@NonNull String attachment) {
        this.attachments.add(attachment);
    }

    public void addAttachments(@NonNull List<String> attachments) {
        this.attachments.addAll(attachments);
    }

    public void setOnSuccessCallback(OnSuccessCallback onSuccessCallback) {
        this.onSuccessCallback = onSuccessCallback;
    }

    public void setOnFailCallback(OnFailCallback onFailCallback) {
        this.onFailCallback = onFailCallback;
    }

    public void send() {
        if (TextUtils.isEmpty(username)) {
            throw new IllegalArgumentException("You didn't set a Gmail username");
        }
        if (TextUtils.isEmpty(password)) {
            throw new IllegalArgumentException("You didn't set a Gmail password");
        }
        if (TextUtils.isEmpty(mailto)) {
            throw new IllegalArgumentException("You didn't set a Gmail recipient");
        }
        if (TextUtils.isEmpty(body)) {
            throw new IllegalArgumentException("You didn't set a body");
        }
        if (TextUtils.isEmpty(subject)) {
            throw new IllegalArgumentException("You didn't set a subject");
        }
        if (!Utils.isNetworkAvailable(mContext)) {
            Log.d(TAG, "you need internet connection to send the email");
        }
        new SendEmailTask().execute();
    }

    public class SendEmailTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Remove or comment out ProgressDialog code
            // if (processVisibility) {
            //     progressDialog = new ProgressDialog(mContext);
            //     progressDialog.setMessage(sendingMessage);
            //     progressDialog.setCancelable(false);
            //     progressDialog.show();
            // }
        }

        @Override
        protected Boolean doInBackground(String... arg0) {
            try {
                GmailSender sender = new GmailSender(username, password);
                if (!attachments.isEmpty()) {
                    for (int i = 0; i < attachments.size(); i++) {
                        if (!attachments.get(i).isEmpty()) {
                            sender.addAttachment(attachments.get(i));
                        }
                    }
                }
                sender.sendMail(subject, body, username, mailto, type);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            // Remove or comment out ProgressDialog and Toast code
            // if (processVisibility) {
            //     progressDialog.dismiss();
            //     if (result) {
            //         if (!TextUtils.isEmpty(sendingMessageSuccess)) {
            //             Toast.makeText(mContext, sendingMessageSuccess, Toast.LENGTH_SHORT).show();
            //         }
            //         if (onSuccessCallback != null) {
            //             onSuccessCallback.onSuccess();
            //         }
            //     } else {
            //         if (!TextUtils.isEmpty(sendingMessageError)) {
            //             Toast.makeText(mContext, sendingMessageError, Toast.LENGTH_SHORT).show();
            //         }
            //         if (onFailCallback != null) {
            //             onFailCallback.onFail();
            //         }
            //     }
            // }
            if (result) {
                if (onSuccessCallback != null) {
                    onSuccessCallback.onSuccess();
                }
            } else {
                if (onFailCallback != null) {
                    onFailCallback.onFail();
                }
            }
        }
    }

    public static final class Builder {
        private Context context;
        private String username;
        private String password;
        private String mailto;
        private String subject;
        private String body;
        private String type = CustomBackgroundMail.TYPE_PLAIN;
        private ArrayList<String> attachments = new ArrayList<>();
        private boolean processVisibility = true;
        private OnSuccessCallback onSuccessCallback;
        private OnFailCallback onFailCallback;

        private Builder(Context context) {
            this.context = context;
        }

        public Builder withUsername(@NonNull String username) {
            this.username = username;
            return this;
        }

        public Builder withPassword(@NonNull String password) {
            this.password = password;
            return this;
        }

        public Builder withMailto(@NonNull String mailto) {
            this.mailto = mailto;
            return this;
        }

        public Builder withSubject(@NonNull String subject) {
            this.subject = subject;
            return this;
        }

        public Builder withType(@NonNull String type) {
            this.type = type;
            return this;
        }

        public Builder withBody(@NonNull String body) {
            this.body = body;
            return this;
        }

        public Builder withAttachments(@NonNull ArrayList<String> attachments) {
            this.attachments.addAll(attachments);
            return this;
        }

        public Builder withProcessVisibility(boolean processVisibility) {
            this.processVisibility = processVisibility;
            return this;
        }

        public Builder withOnSuccessCallback(OnSuccessCallback onSuccessCallback) {
            this.onSuccessCallback = onSuccessCallback;
            return this;
        }

        public Builder withOnFailCallback(OnFailCallback onFailCallback) {
            this.onFailCallback = onFailCallback;
            return this;
        }

        public CustomBackgroundMail build() {
            return new CustomBackgroundMail(this);
        }

        public CustomBackgroundMail send() {
            CustomBackgroundMail customBackgroundMail = build();
            customBackgroundMail.send();
            return customBackgroundMail;
        }
    }
}
