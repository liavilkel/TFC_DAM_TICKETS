package com.example.tfc_dam_tickets;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;

import android.content.DialogInterface;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tfc_dam_tickets.model.Client;
import com.example.tfc_dam_tickets.model.Ticket;
import com.example.tfc_dam_tickets.model.User;
import com.example.tfc_dam_tickets.persistence.ClientPersistence;
import com.example.tfc_dam_tickets.persistence.TicketPersistence;
import com.example.tfc_dam_tickets.persistence.UserPersistence;
import com.example.tfc_dam_tickets.utils.EmailSender;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SuppressLint("NewApi")
public class ActivityDetalleTicket extends BaseActivity {

    TextView tvDetalleIdTicket;
    TextView tvDetalleFechaIni;
    TextView tvDetalleFechaFin;
    TextView tvDetalleHoraIni;
    TextView tvDetalleHoraFin;
    TextView tvDetalleTitulo;
    TextView tvDetalleDescripcion;
    TextView tvDetalleEstado;
    TextView tvDetalleCliente;
    TextView tvDetalleCalle;
    TextView tvDetalleCP;
    TextView tvDetalleMunicipio;
    TextView tvDetalleProvincia;
    TextView tvDetalleNomUsuario;
    TextView tvDetalleApellUsuario;
    TextView tvDetalleTelfUsuario;
    TextView tvDetalleEmailUsuario;
    EditText etSolucionTecnico;
    Button btnDetalleGuardar;
    Button btnDetalleCancelar;
    Spinner spinner;

    TicketPersistence ticketPersistence;
    UserPersistence userPersistence;
    ClientPersistence clientPersistence;

    String selectedItem = null;

    User user = null;
    User loggedUser = null;
    Ticket ticket = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_ticket);
        Toolbar customToolbar = findViewById(R.id.custom_actionbar);
        setSupportActionBar(customToolbar);
        initializeUI();

        ticketPersistence = new TicketPersistence(this);
        userPersistence = new UserPersistence(this);
        clientPersistence = new ClientPersistence(this);

        Intent i = getIntent();
        Long ticketId = i.getLongExtra("ticketId", -1);
        String loggedEmail = i.getStringExtra("loggedEmail");

        ticket = ticketPersistence.getTicketById(ticketId);
        user = userPersistence.getUserByEmail(ticket.getUserOpen());
        loggedUser = userPersistence.getUserByEmail(loggedEmail);
        Client client = clientPersistence.findClientById(user.getComId());

        if (loggedUser.getType().equals("tecnico")) {
            initFields(true, ticket.getStatus());
            fillInFields(ticket, user, client, true, ticketId);
        } else {
            initFields(false, ticket.getStatus());
            fillInFields(ticket, user, client, false, ticketId);
        }


        btnDetalleCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityDetalleTicket.this, R.string.toast_cancelar_detalle_ticket, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ActivityDetalleTicket.this, TicketsList.class);
                i.putExtra("catId", Integer.parseInt(String.valueOf(ticket.getCatId())));
                i.putExtra("email", loggedUser.getEmail());
                startActivity(i);
            }
        });

        btnDetalleGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedItem.equals(getString(R.string.cerrado))) {
                    new AlertDialog.Builder(ActivityDetalleTicket.this)
                            .setTitle(R.string.confirmar_cierre_ticket)
                            .setMessage(R.string.desea_cambiar_el_estado)
                            .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    guardarTicket(ticket);
                                }
                            })
                            .setNegativeButton(R.string.cancelar, null)
                            .show();
                } else {
                    guardarTicket(ticket);
                }
            }
        });
    }

    private void guardarTicket(Ticket ticket) {

        String oldStatus = ticket.getStatus();
        ticket.setStatus(selectedItem);

        if (ticket.getStatus().equals(getString(R.string.cerrado))) {
            ticket.setTsClose(LocalDateTime.now());
        }
        if (!etSolucionTecnico.getText().toString().isBlank()) {
            ticket.setSolution(etSolucionTecnico.getText().toString());
        }

        int res = ticketPersistence.updateTicket(ticket);

        if (res == 1) {

            if (!oldStatus.equals(ticket.getStatus())){
                String body = getString(R.string.ticket_status_update, user.getName()
                        ,ticket.getTicketId(), ticket.getStatus());
                String subject = getString(R.string.ticket_status_update_subject, ticket.getTicketId());
                EmailSender.sendEmail(this, user.getEmail(), subject, body);
            }

            Toast.makeText(ActivityDetalleTicket.this, R.string.toast_guardar_detalle_ticket, Toast.LENGTH_SHORT).show();
            Intent i = new Intent(ActivityDetalleTicket.this, TicketsList.class);
            i.putExtra("catId", Integer.parseInt(String.valueOf(ticket.getCatId())));
            i.putExtra("email", loggedUser.getEmail());
            startActivity(i);
        } else {
            Toast.makeText(ActivityDetalleTicket.this, R.string.ticket_update_fail, Toast.LENGTH_SHORT).show();
        }
    }

    private void initializeUI() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.tv_titulo_detalle);
        }
    }


    private void fillInFields(Ticket ticket, User user, Client client, Boolean tecnico, Long ticketId) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm");
        String formattedDate;
        String formattedTime;

        String[] items = new String[]{String.valueOf((R.string.nuevo)), String.valueOf((R.string.en_proceso)), String.valueOf((R.string.cerrado))};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Nothing to do here
            }
        });

        if (ticket != null && user != null && client != null) {
            tvDetalleIdTicket.setText("#ID " + String.valueOf(ticketId));
            formattedDate = ticket.getTsOpen().format(formatter);
            tvDetalleFechaIni.setText(getString(R.string.fecha_ini) + formattedDate);
            formattedDate = (ticket.getTsClose() != null) ? ticket.getTsClose().format(formatter) : "---";
            tvDetalleFechaFin.setText(getString(R.string.fecha_fin) + formattedDate);
            formattedTime = ticket.getTsOpen().format(formatter2);
            tvDetalleHoraIni.setText(getString(R.string.hora_ini) + formattedTime);
            formattedTime = (ticket.getTsClose() != null) ? ticket.getTsClose().format(formatter2) : "---";
            tvDetalleHoraFin.setText(getString(R.string.hora_fin)+ formattedTime);
            tvDetalleTitulo.setText(getString(R.string.titulo) + ticket.getTitle());
            tvDetalleDescripcion.setText(getString(R.string.descripcion)+ ticket.getDescription());
            spinner.setSelection(adapter.getPosition(ticket.getStatus()));
            String sol = ticket.getDescription() != null ? ticket.getSolution() : "";
            etSolucionTecnico.setText(sol);

            if (tecnico) {
                spinner.setEnabled(true);
                spinner.setFocusable(true);
                etSolucionTecnico.setEnabled(true);
            } else {
                spinner.setEnabled(false);
                spinner.setFocusable(false);
                etSolucionTecnico.setEnabled(false);
            }

            tvDetalleCliente.setText(getString(R.string.cliente) + client.getName());
            tvDetalleCalle.setText(getString(R.string.calle) + client.getStreet());
            tvDetalleCP.setText(getString(R.string.cp) + client.getZipCode());
            tvDetalleMunicipio.setText(getString(R.string.municipio) + client.getMunicipality());
            tvDetalleProvincia.setText(getString(R.string.provincia)+ client.getProvince());

            tvDetalleNomUsuario.setText(getString(R.string.nombre) + user.getName());
            tvDetalleApellUsuario.setText(getString(R.string.apellidos) + user.getLastName());
            tvDetalleTelfUsuario.setText(getString(R.string.telf) + user.getPhoneNum());
            tvDetalleEmailUsuario.setText(getString(R.string.email_detalle) + user.getEmail());

        } else {
            Toast.makeText(ActivityDetalleTicket.this, R.string.toast_unable_fetch_data_detalle,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void initFields(Boolean tecnico, String status) {
        // Inicialización de elementos de la vista
        tvDetalleIdTicket = findViewById(R.id.tvDetalleIdTicket);
        tvDetalleFechaIni = findViewById(R.id.tvDetalleFechaIni);
        tvDetalleFechaFin = findViewById(R.id.tvDetalleFechaFin);
        tvDetalleHoraIni = findViewById(R.id.tvDetalleHoraIni);
        tvDetalleHoraFin = findViewById(R.id.tvDetalleHoraFin);
        tvDetalleTitulo = findViewById(R.id.tvDetalleTitulo);
        tvDetalleDescripcion = findViewById(R.id.tvDetalleDescripcion);
        tvDetalleEstado = findViewById(R.id.tvDetalleEstado);
        tvDetalleCliente = findViewById(R.id.tvDetalleCliente);
        tvDetalleCalle = findViewById(R.id.tvDetalleCalle);
        tvDetalleCP = findViewById(R.id.tvDetalleCP);
        tvDetalleMunicipio = findViewById(R.id.tvDetalleMunicipio);
        tvDetalleProvincia = findViewById(R.id.tvDetalleProvincia);
        tvDetalleNomUsuario = findViewById(R.id.tvDetalleNomUsuario);
        tvDetalleApellUsuario = findViewById(R.id.tvDetalleApellUsuario);
        tvDetalleTelfUsuario = findViewById(R.id.tvDetalleTelfUsuario);
        tvDetalleEmailUsuario = findViewById(R.id.tvDetalleEmailUsuario);
        etSolucionTecnico = findViewById(R.id.etSolucionTecnico);
        btnDetalleGuardar = findViewById(R.id.btnDetalleGuardar);
        btnDetalleCancelar = findViewById(R.id.btnDetalleCancelar);
        spinner = findViewById(R.id.spinner);

        if(tecnico && !status.equals(R.string.cerrado)) {
            btnDetalleGuardar.setEnabled(true);
            btnDetalleGuardar.setVisibility(View.VISIBLE);
            btnDetalleCancelar.setEnabled(true);
            btnDetalleCancelar.setVisibility(View.VISIBLE);
            spinner.setEnabled(true);
            spinner.setFocusable(true);
        } else {
            btnDetalleGuardar.setEnabled(false);
            btnDetalleGuardar.setVisibility(View.GONE);
            btnDetalleCancelar.setEnabled(true);
            btnDetalleCancelar.setVisibility(View.GONE);
            etSolucionTecnico.setEnabled(false);
            etSolucionTecnico.setFocusable(false);
            etSolucionTecnico.setFocusableInTouchMode(false);
            etSolucionTecnico.setAlpha(0.5f);
            spinner.setEnabled(false);
            spinner.setFocusable(false);
            spinner.setAlpha(0.5f); // Optional: Make it look disabled by changing its opacity
            spinner.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true; // Consume touch events
                }
            });
        }
    }
}