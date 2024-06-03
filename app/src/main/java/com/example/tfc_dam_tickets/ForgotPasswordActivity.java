package com.example.tfc_dam_tickets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText etEmail;
    Button recPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        etEmail = findViewById(R.id.etRecuperarPass);
        recPassword = findViewById(R.id.btnRecuperarPass);

        recPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etEmail.getText().toString().isBlank()) {
                    Toast.makeText(ForgotPasswordActivity.this, R.string.no_email, Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(ForgotPasswordActivity.this, EnterRecCodeActivity.class);
                    i.putExtra("email", etEmail.getText().toString());
                    startActivity(i);
                }

            }
        });

    }
}