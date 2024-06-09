package com.example.tfc_dam_tickets;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tfc_dam_tickets.persistence.CategoryPersistence;
import com.example.tfc_dam_tickets.persistence.ClientPersistence;
import com.example.tfc_dam_tickets.persistence.TicketPersistence;
import com.example.tfc_dam_tickets.persistence.UserPersistence;
import com.example.tfc_dam_tickets.utils.EmailSender;
import com.google.android.material.textfield.TextInputLayout;

public class ActivityNuevoTicket extends BaseActivity {
    TextInputLayout etTitulo, etDescripcion, etCat;
    Button btnCancelar;
    Button btnAceptar;
    Integer catId;

    CategoryPersistence categoryPersistence;
    UserPersistence userPersistence;
    TicketPersistence ticketPersistence;
    ClientPersistence clientPersistence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_ticket);

        Toolbar customToolbar = findViewById(R.id.custom_actionbar);
        setSupportActionBar(customToolbar);
        initializeUI();

        categoryPersistence = new CategoryPersistence(this);
        userPersistence = new UserPersistence(this);
        ticketPersistence = new TicketPersistence(this);
        clientPersistence = new ClientPersistence(this);

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
                    insert = -100; // OTHER number so actForResult does not finish
                }

                if (insert == 1) {
                    EmailSender.notifyAdmins(getApplicationContext(), userPersistence.getUserByEmail(userOpen),
                            ticketPersistence.getLatestTicketByUser(userOpen), clientPersistence.findClientById(clientId));
                    setResult(1);
                    finish();
                } else if (insert == -1){
                    setResult(-1);
                    finish();
                }
            }
        });
    }

    private void initializeUI() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.tv_titulo_nuevoTicket);
        }
    }
}