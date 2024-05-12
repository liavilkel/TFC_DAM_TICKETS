package com.example.tfc_dam_tickets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

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


        //Inicializar el RecyclerVire de tickets
        recyclerViewTickets = findViewById(R.id.rvTicketsList);
        recyclerViewTickets.setHasFixedSize(true);
        recyclerViewTickets.setLayoutManager(new LinearLayoutManager(this));

        //Inicializar el Boton de registrar nuevo ticket
        btnAdd = findViewById(R.id.btnAddTicket);

        ticketPersistence = new TicketPersistence(this);

        loadTickets();
    }
    private void loadTickets() {
        ArrayList<Ticket> tickets = ticketPersistence.cargarTodosTickets();
        adapterTicket = new AdapterTicket(tickets);
        recyclerViewTickets.setAdapter(adapterTicket);
    }
}