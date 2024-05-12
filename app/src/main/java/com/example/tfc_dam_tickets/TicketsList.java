package com.example.tfc_dam_tickets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TicketsList extends AppCompatActivity {

    RecyclerView recyclerViewTickets;
    Button btnAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets_list);

        //Inicializar el RecyclerVire de tickets
        recyclerViewTickets = findViewById(R.id.rvTicketsList);
        recyclerViewTickets.setHasFixedSize(true);
        recyclerViewTickets.setLayoutManager(new LinearLayoutManager(this));

        //Inicializar el Boton de registrar nuevo ticket
        btnAdd = findViewById(R.id.btnAddTicket);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TicketsList.this, ActivityNuevoTicket.class);
                startActivity(intent);
            }
        });
    }
}