package com.example.tfc_dam_tickets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tfc_dam_tickets.persistence.UserPersistence;

public class EnterRecCodeActivity extends AppCompatActivity {

    EditText etRecRode;
    Button btnCheckCode;
    UserPersistence userPersistence;

    Boolean legit = false;
    String code = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_rec_code);

        userPersistence = new UserPersistence(this);
        etRecRode = findViewById(R.id.etRecCode);
        btnCheckCode = findViewById(R.id.btnCheckRecCode);

        Intent i = getIntent();
        String email = i.getStringExtra("email");

        if (userPersistence.isEmailInUse(email)) {
            legit = true;
            code = userPersistence.getUserRecCode(email);
            Toast.makeText(EnterRecCodeActivity.this, code, Toast.LENGTH_LONG).show();
        }

        btnCheckCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (legit) {
                    if(etRecRode.getText().toString().equals(code)) {
                        //TODO GENERATE NEW CODE
                        //TODO SET NEW PASSWORD IN HASH
                        Intent i = new Intent(EnterRecCodeActivity.this, SetNewPasswordActivity.class);
                        i.putExtra("email", email);
                        startActivity(i);
                    }
                } else {
                    Toast.makeText(EnterRecCodeActivity.this, R.string.incorrect_rec_code, Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}