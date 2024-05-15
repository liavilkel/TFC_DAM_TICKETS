package com.example.tfc_dam_tickets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tfc_dam_tickets.persistence.CategoryPersistence;
import com.example.tfc_dam_tickets.persistence.TicketPersistence;
import com.example.tfc_dam_tickets.persistence.UserPersistence;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDateTime;

public class ActivityNuevoTicket extends AppCompatActivity {
    TextInputLayout etTitulo, etDescripcion, etCat;
    Button btnCancelar;
    Button btnAceptar;
    Integer catId;

    CategoryPersistence categoryPersistence;
    UserPersistence userPersistence;
    TicketPersistence ticketPersistence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_ticket);

        categoryPersistence = new CategoryPersistence(this);
        userPersistence = new UserPersistence(this);
        ticketPersistence = new TicketPersistence(this);

        Intent i = getIntent();

        etTitulo = findViewById(R.id.etTitulo);
        etDescripcion = findViewById(R.id.etDescripcion);
        etCat = findViewById(R.id.etCat);
        catId = i.getIntExtra("catId", -1);
        etCat.getEditText().setText(categoryPersistence.getNameById(catId));
        etCat.setEnabled(false);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnAceptar = findViewById(R.id.btnAceptar);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(0);
                finish();
            }
        });

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userOpen = i.getStringExtra("email");
                Long clientId = userPersistence.getClientIdByEmail(userOpen);
                String tit = etTitulo.getEditText().getText().toString();
                String desc = etDescripcion.getEditText().getText().toString();
                String status = "Nuevo";

                byte insert = -1;

                if (!tit.isBlank() && !desc.isBlank()) {
                    insert = ticketPersistence.postNewTicket(userOpen, Long.valueOf(catId), clientId, tit, desc, status);
                } else {
                    Toast.makeText(ActivityNuevoTicket.this, R.string.all_mandaory, Toast.LENGTH_SHORT).show();
                    insert = -100; // OTHER number SO actForResult does not finish
                }

                if (insert == 1) {
                    setResult(1);
                    finish();
                } else if (insert == -1){
                    setResult(-1);
                    finish();
                }
            }
        });
    }
}