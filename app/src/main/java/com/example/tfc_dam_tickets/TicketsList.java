package com.example.tfc_dam_tickets;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tfc_dam_tickets.adapterUtils.AdapterTicket;
import com.example.tfc_dam_tickets.model.Ticket;
import com.example.tfc_dam_tickets.persistence.TicketPersistence;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class TicketsList extends BaseActivity {

    RecyclerView recyclerViewTickets;
    AdapterTicket adapterTicket;
    TicketPersistence ticketPersistence;
    Button btnAdd;
    Intent i;
    Spinner spinner;
    String selectedStatus = getString(R.string.todos);

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
                            } else if (resultCode == -1) {
                                Toast.makeText(TicketsList.this, R.string.unable_save_ticker, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets_list);

        ticketPersistence = new TicketPersistence(this);

        Toolbar customToolbar = findViewById(R.id.custom_actionbar);
        setSupportActionBar(customToolbar);
        initializeUI();


        recyclerViewTickets = findViewById(R.id.rvTicketsList);
        recyclerViewTickets.setHasFixedSize(true);
        recyclerViewTickets.setLayoutManager(new LinearLayoutManager(this));

        btnAdd = findViewById(R.id.btnAddTicket);
        spinner = findViewById(R.id.spinner_ticket_list);

        String[] statusOptions = {getString(R.string.todos), getString(R.string.en_proceso), getString(R.string.cerrado), getString(R.string.nuevo)};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, statusOptions
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setSelection(0);
        spinner.setAdapter(adapter);

        i = getIntent();
        if (i.hasExtra("catId")) {
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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStatus = parent.getItemAtPosition(position).toString();
                cargarTickets(i.getIntExtra("catId", -1));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinner.setSelection(adapter.getPosition(getString(R.string.todos)));
                cargarTickets(i.getIntExtra("catId", -1));
            }
        });

    }

    private void cargarTickets(int cat) {
        ArrayList<Ticket> tickets = ticketPersistence.getTicketsByCat(cat);

        if (selectedStatus.equals(getString(R.string.todos))) {
            adapterTicket = new AdapterTicket(this, tickets, i.getStringExtra("email"));
        } else {
            ArrayList<Ticket> filteredTickets = new ArrayList<>();
            filteredTickets.addAll(tickets.stream()
                    .filter(t -> t.getStatus().equals(selectedStatus))
                    .collect(Collectors.toList()));

            adapterTicket = new AdapterTicket(this, filteredTickets, i.getStringExtra("email"));
        }

        recyclerViewTickets.setAdapter(adapterTicket);
    }

    private void initializeUI() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.tv_titulo_listaTickets);
        }

    }
}