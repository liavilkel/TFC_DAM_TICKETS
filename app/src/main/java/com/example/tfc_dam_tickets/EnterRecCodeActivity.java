package com.example.tfc_dam_tickets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tfc_dam_tickets.persistence.UserPersistence;
import com.example.tfc_dam_tickets.utils.EmailSender;

public class EnterRecCodeActivity extends AppCompatActivity {
    //private static final String SUBJECT = "Código de recuperación ResolverRocket";
    EditText etRecRode;
    Button btnCheckCode;
    UserPersistence userPersistence;
    TextView tvPulseAqui;

    Boolean legit = false;
    String code = null;
    String subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_rec_code);

        subject = getString(R.string.subject_codigo_rec);

        userPersistence = new UserPersistence(this);
        etRecRode = findViewById(R.id.etRecCode);
        btnCheckCode = findViewById(R.id.btnCheckRecCode);
        tvPulseAqui = findViewById(R.id.tvPulseAquiRecCode);

        Intent i = getIntent();
        String email = i.getStringExtra("email");

        if (userPersistence.isEmailInUse(email)) {
            legit = true;
            code = userPersistence.getUserRecCode(email);
            String body = getString(R.string.verification_body, code);
            EmailSender.sendEmail(this, email, subject, body);
        }

        btnCheckCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (legit) {
                    if(etRecRode.getText().toString().trim().equals(code)) {
                        Intent i = new Intent(EnterRecCodeActivity.this, SetNewPasswordActivity.class);
                        i.putExtra("email", email);
                        i.putExtra("outcome", "login");
                        startActivity(i);
                    } else {
                        Toast.makeText(EnterRecCodeActivity.this, R.string.incorrect_rec_code, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(EnterRecCodeActivity.this, R.string.incorrect_rec_code, Toast.LENGTH_LONG).show();
                }
            }
        });

        tvPulseAqui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}