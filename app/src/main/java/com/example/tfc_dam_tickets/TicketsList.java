package com.example.tfc_dam_tickets;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
    Intent i;

    ActivityResultLauncher<Intent> startActivityForResult = 
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult ar) {
                        
                            int resultCode = ar.getResultCode();
                            
                            if (resultCode == 1) {
                                Toast.makeText(TicketsList.this, R.string.toast_guardar_new_ticket, Toast.LENGTH_SHORT).show();
                                cargarTickets(i.getIntExtra("catId", -1));
                                adapterTicket.notifyDataSetChanged();
                            } else if (resultCode == 0) {
                                Toast.makeText(TicketsList.this, R.string.toast_cancelar_new_ticket, Toast.LENGTH_SHORT).show();
                            } else if (resultCode == -1){
                                Toast.makeText(TicketsList.this, R.string.unable_save_ticker, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets_list);

        recyclerViewTickets = findViewById(R.id.rvTicketsList);
        recyclerViewTickets.setHasFixedSize(true);
        recyclerViewTickets.setLayoutManager(new LinearLayoutManager(this));

        btnAdd = findViewById(R.id.btnAddTicket);

        ticketPersistence = new TicketPersistence(this);

        i = getIntent();
        if (i.hasExtra("catId")){
           cargarTickets(i.getIntExtra("catId", -1));
        }




        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TicketsList.this, ActivityNuevoTicket.class);
                intent.putExtra("catId", i.getIntExtra("catId", -1));
                intent.putExtra("email", i.getStringExtra("email"));
                startActivityForResult.launch(intent);

            }
        });

    }
    private void cargarTickets(int cant) {
        ArrayList<Ticket> tickets = ticketPersistence.getTicketsByCat(cant);

        adapterTicket = new AdapterTicket(this, tickets);
        recyclerViewTickets.setAdapter(adapterTicket);
    }
}