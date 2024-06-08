package com.example.tfc_dam_tickets.utils;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.example.tfc_dam_tickets.R;
import java.util.regex.Pattern;

public class PasswordValidator {

    private static final Pattern PASSWORD_PATTERN_UPPERCASE = Pattern.compile(".*[A-Z].*");
    private static final Pattern PASSWORD_PATTERN_LOWERCASE = Pattern.compile(".*[a-z].*");
    private static final Pattern PASSWORD_PATTERN_DIGIT = Pattern.compile(".*[0-9].*");
    private static final Pattern PASSWORD_PATTERN_SPECIAL = Pattern.compile(".*[!¡¿?.@#$%^&+=].*");

    public static void addPasswordValidation(final Context context, final EditText passwordEditText,
                                             final TextView tvPasswordLength, final TextView tvPasswordUppercase,
                                             final TextView tvPasswordLowercase, final TextView tvPasswordDigit,
                                             final TextView tvPasswordSpecialChar) {

        // Añadir TextWatcher al EditText para escuchar los cambios en el texto
        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validatePassword(context, passwordEditText.getText().toString(), tvPasswordLength, tvPasswordUppercase,
                        tvPasswordLowercase, tvPasswordDigit, tvPasswordSpecialChar);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private static void validatePassword(Context context, String passwordInput, TextView tvPasswordLength,
                                         TextView tvPasswordUppercase, TextView tvPasswordLowercase,
                                         TextView tvPasswordDigit, TextView tvPasswordSpecialChar) {

        boolean isValid = true;

        if (passwordInput.length() >= 8) {
            tvPasswordLength.setTextColor(ContextCompat.getColor(context, R.color.green));
        } else {
            tvPasswordLength.setTextColor(ContextCompat.getColor(context, R.color.red));
            isValid = false;
        }

        if (PASSWORD_PATTERN_UPPERCASE.matcher(passwordInput).matches()) {
            tvPasswordUppercase.setTextColor(ContextCompat.getColor(context, R.color.green));
        } else {
            tvPasswordUppercase.setTextColor(ContextCompat.getColor(context, R.color.red));
            isValid = false;
        }

        if (PASSWORD_PATTERN_LOWERCASE.matcher(passwordInput).matches()) {
            tvPasswordLowercase.setTextColor(ContextCompat.getColor(context, R.color.green));
        } else {
            tvPasswordLowercase.setTextColor(ContextCompat.getColor(context, R.color.red));
            isValid = false;
        }

        if (PASSWORD_PATTERN_DIGIT.matcher(passwordInput).matches()) {
            tvPasswordDigit.setTextColor(ContextCompat.getColor(context, R.color.green));
        } else {
            tvPasswordDigit.setTextColor(ContextCompat.getColor(context, R.color.red));
            isValid = false;
        }

        if (PASSWORD_PATTERN_SPECIAL.matcher(passwordInput).matches()) {
            tvPasswordSpecialChar.setTextColor(ContextCompat.getColor(context, R.color.green));
        } else {
            tvPasswordSpecialChar.setTextColor(ContextCompat.getColor(context, R.color.red));
            isValid = false;
        }
    }

    public static boolean isPasswordValid(String password) {
        return password.length() >= 8 &&
                PASSWORD_PATTERN_UPPERCASE.matcher(password).matches() &&
                PASSWORD_PATTERN_LOWERCASE.matcher(password).matches() &&
                PASSWORD_PATTERN_DIGIT.matcher(password).matches() &&
                PASSWORD_PATTERN_SPECIAL.matcher(password).matches();
    }
}
