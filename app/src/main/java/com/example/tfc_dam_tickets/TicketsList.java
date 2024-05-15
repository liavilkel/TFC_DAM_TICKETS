package com.example.tfc_dam_tickets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tfc_dam_tickets.adapterUtils.AdapterTicket;
import com.example.tfc_dam_tickets.model.Ticket;
import com.example.tfc_dam_tickets.persistence.TicketPersistence;

import java.util.ArrayList;

public class TicketsList extends AppCompatActivity {

    RecyclerView recyclerViewTickets;
    AdapterTicket adapterTicket;
    TicketPersistence ticketPersistence;
    Button btnAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets_list);

        recyclerViewTickets = findViewById(R.id.rvTicketsList);
        recyclerViewTickets.setHasFixedSize(true);
        recyclerViewTickets.setLayoutManager(new LinearLayoutManager(this));

        btnAdd = findViewById(R.id.btnAddTicket);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TicketsList.this, ActivityNuevoTicket.class);
                startActivity(intent);
            }
        });

        ticketPersistence = new TicketPersistence(this);

        Intent i = getIntent();

        if (i.hasExtra("id")){
           cargarTickets(i.getIntExtra("id", -1));
        }
    }
    private void cargarTickets(int cant) {
        ArrayList<Ticket> tickets = ticketPersistence.getTicketsByCat(cant);
        adapterTicket = new AdapterTicket(this, tickets);
        recyclerViewTickets.setAdapter(adapterTicket);

    }
}