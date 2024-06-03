package com.example.tfc_dam_tickets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tfc_dam_tickets.persistence.UserPersistence;
import com.example.tfc_dam_tickets.utils.RandomCodeGenerator;

import org.mindrot.jbcrypt.BCrypt;

public class SetNewPasswordActivity extends AppCompatActivity {

    UserPersistence userPersistence;
    EditText etNewPassword;
    Button btnSetPassword;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);

        userPersistence = new UserPersistence(this);

        Intent i = getIntent();
        email = i.getStringExtra("email");

        etNewPassword = findViewById(R.id.etNewPassword);
        btnSetPassword = findViewById(R.id.btnSetNewPassword);

        btnSetPassword.setOnClickListener(new View.OnClickListener() {

            // TODO check password, SEND EMAIL, CHECK IT ACTUALLY WORKS LOL

            @Override
            public void onClick(View v) {
                if (!etNewPassword.getText().toString().isBlank()) {
                    String hashedPassword = BCrypt.hashpw(etNewPassword.getText().toString(), BCrypt.gensalt());
                    Boolean success = userPersistence.updatePasswordAndRecoveryCode
                            (email, hashedPassword, RandomCodeGenerator.generateRandomCode());
                } else {
                    // CHECK FOR PASSWORD
                }
            }
        });

    }
}