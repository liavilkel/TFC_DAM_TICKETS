package com.example.tfc_dam_tickets;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class ActivityNuevoTicket extends AppCompatActivity {
    TextInputLayout etTitulo;
    TextInputLayout etDescripcion;
    Button btnCancelar;
    Button btnAceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_ticket);

        etTitulo = findViewById(R.id.etTitulo);
        etDescripcion = findViewById(R.id.etDescripcion);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnAceptar = findViewById(R.id.btnAceptar);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityNuevoTicket.this, R.string.toast_cancelar_new_ticket, Toast.LENGTH_SHORT).show();
            }
        });

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityNuevoTicket.this, R.string.toast_guardar_new_ticket, Toast.LENGTH_SHORT).show();
            }
        });
    }
}