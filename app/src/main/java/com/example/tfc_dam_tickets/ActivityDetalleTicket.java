package com.example.tfc_dam_tickets;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

<<<<<<< HEAD

import android.annotation.SuppressLint;
import android.content.DialogInterface;
=======
import android.annotation.SuppressLint;

import android.content.DialogInterface;

>>>>>>> ef687fb6dff299a5f176b0a557aed3d93343da43
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;
<<<<<<< HEAD
import com.example.tfc_dam_tickets.autenticacion.Login;
=======

import com.example.tfc_dam_tickets.autenticacion.Login;

>>>>>>> ef687fb6dff299a5f176b0a557aed3d93343da43
import com.example.tfc_dam_tickets.model.Client;
import com.example.tfc_dam_tickets.model.Ticket;
import com.example.tfc_dam_tickets.model.User;
import com.example.tfc_dam_tickets.persistence.ClientPersistence;
import com.example.tfc_dam_tickets.persistence.TicketPersistence;
import com.example.tfc_dam_tickets.persistence.UserPersistence;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



@SuppressLint("NewApi")
public class ActivityDetalleTicket extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //TODO ADEVERTENCIA TICKET SET CERRADO NO MODIFCABLE

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_ticket);
        initializeUI();

        Toolbar customToolbar = findViewById(R.id.custom_actionbar);
        setSupportActionBar(customToolbar);

        ticketPersistence = new TicketPersistence(this);
        userPersistence = new UserPersistence(this);
        clientPersistence = new ClientPersistence(this);

        Intent i = getIntent();
        Long ticketId = i.getLongExtra("ticketId", -1);

        Ticket ticket = ticketPersistence.getTicketById(ticketId);
        User user = userPersistence.getUserByEmail(ticket.getUserOpen());
        Client client = clientPersistence.findClientById(user.getComId());

        if (user.getType().equals("tecnico")) {
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
                startActivity(i);
            }
        });

        btnDetalleGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< HEAD

                ticket.setStatus(selectedItem);
                if (ticket.getStatus().equals("Cerrado")){
                    ticket.setTsClose(LocalDateTime.now());
                }
                if (!etSolucionTecnico.getText().toString().isBlank()){
                    ticket.setSolution(etSolucionTecnico.getText().toString());
                }

                int res = ticketPersistence.updateTicket(ticket);

                if (res == 1) {
                    Toast.makeText(ActivityDetalleTicket.this, R.string.toast_guardar_detalle_ticket, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ActivityDetalleTicket.this, TicketsList.class);
                    i.putExtra("catId", Integer.parseInt(String.valueOf(ticket.getCatId())));
                    startActivity(i);
=======
                if (selectedItem.equals("Cerrado")) {
                    new AlertDialog.Builder(ActivityDetalleTicket.this)
                            .setTitle("Confirmar cierre de ticket")
                            .setMessage("¿Desea cambiar el estado a Cerrado? Una vez modificado, el ticket dejará de estar disponible.")
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    guardarTicket(ticket);
                                }
                            })
                            .setNegativeButton("Cancelar", null)
                            .show();
>>>>>>> ef687fb6dff299a5f176b0a557aed3d93343da43
                } else {
                    guardarTicket(ticket);
                }
            }
        });
    }

    private void guardarTicket(Ticket ticket) {
        sendEmail();

        ticket.setStatus(selectedItem);
        if (ticket.getStatus().equals("Cerrado")) {
            ticket.setTsClose(LocalDateTime.now());
        }
        if (!etSolucionTecnico.getText().toString().isBlank()) {
            ticket.setSolution(etSolucionTecnico.getText().toString());
        }

        int res = ticketPersistence.updateTicket(ticket);

        if (res == 1) {
            Toast.makeText(ActivityDetalleTicket.this, R.string.toast_guardar_detalle_ticket, Toast.LENGTH_SHORT).show();
            Intent i = new Intent(ActivityDetalleTicket.this, TicketsList.class);
            i.putExtra("catId", Integer.parseInt(String.valueOf(ticket.getCatId())));
            startActivity(i);
        } else {
            Toast.makeText(ActivityDetalleTicket.this, R.string.ticket_update_fail, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private void initializeUI() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.tv_titulo_detalle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if (item.getItemId() == R.id.mnExit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setTitle("Confirmar salida");
            builder.setMessage("¿Estás seguro de que quieres salir de la aplicación?");
            builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finishAffinity();
                    System.exit(0);
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else if (item.getItemId() == R.id.mnLogOut) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setTitle("Cerrar sesión");
            builder.setMessage("¿Estás seguro de que quieres cerrar la sesión?");
            builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences preferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.remove("user_id");
                    editor.remove("session_token");
                    editor.apply();

                    Intent intent = new Intent(ActivityDetalleTicket.this, Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void fillInFields(Ticket ticket, User user, Client client, Boolean tecnico, Long ticketId) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm");
        String formattedDate;
        String formattedTime;

        String[] items = new String[]{"Nuevo", "En proceso", "Cerrado"};
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
            tvDetalleFechaIni.setText("FECHA INI: " + formattedDate);
            formattedDate = (ticket.getTsClose() != null) ? ticket.getTsClose().format(formatter) : "---";
            tvDetalleFechaFin.setText("FECHA FIN: " + formattedDate);
            formattedTime = ticket.getTsOpen().format(formatter2);
            tvDetalleHoraIni.setText("HORA INI: " + formattedTime);
            formattedTime = (ticket.getTsClose() != null) ? ticket.getTsClose().format(formatter2) : "---";
            tvDetalleHoraFin.setText("HORA FIN: " + formattedTime);
            tvDetalleTitulo.setText("TÍTULO: " + ticket.getTitle());
            tvDetalleDescripcion.setText("DESCRIPCIÓN: " + ticket.getDescription());
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

            tvDetalleCliente.setText("CLIENTE: " + client.getName());
            tvDetalleCalle.setText("CALLE: " + client.getStreet());
            tvDetalleCP.setText("CP: " + client.getZipCode());
            tvDetalleMunicipio.setText("MUNICIPIO: " + client.getMunicipality());
            tvDetalleProvincia.setText("PROVINCIA: " + client.getProvince());

            tvDetalleNomUsuario.setText("NOMBRE: " + user.getName());
            tvDetalleApellUsuario.setText("APELLIDOS: " + user.getLastName());
            tvDetalleTelfUsuario.setText("TELF: " + user.getPhoneNum());
            tvDetalleEmailUsuario.setText("EMAIL: " + user.getEmail());

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

        if(tecnico && !status.equals("Cerrado")) {
            btnDetalleGuardar.setEnabled(true);
            btnDetalleGuardar.setVisibility(View.VISIBLE);
            btnDetalleCancelar.setEnabled(true);
            btnDetalleCancelar.setVisibility(View.VISIBLE);
        } else {
            btnDetalleGuardar.setEnabled(false);
            btnDetalleGuardar.setVisibility(View.GONE);
            btnDetalleCancelar.setEnabled(true);
            btnDetalleCancelar.setVisibility(View.GONE);
        }
    }
}