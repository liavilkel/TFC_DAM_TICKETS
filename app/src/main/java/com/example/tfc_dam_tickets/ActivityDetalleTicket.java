package com.example.tfc_dam_tickets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.android.material.textfield.TextInputLayout;

import java.time.format.DateTimeFormatter;

public class ActivityDetalleTicket extends AppCompatActivity {
    TextInputLayout etTitulo;
    TextInputLayout etDescripcion;
    Button btnCancelar;
    Button btnAceptar;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_ticket);

        ticketPersistence = new TicketPersistence(this);
        userPersistence = new UserPersistence(this);
        clientPersistence = new ClientPersistence(this);

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

        Intent i = getIntent();
        Long ticketId = i.getLongExtra("ticketId", -1);

        Ticket ticket = ticketPersistence.getTicketById(ticketId);
        User user = userPersistence.getUserByEmail(ticket.getUserOpen());
        Client client = clientPersistence.findClientById(user.getComId());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm");
        String formattedDate;
        String formattedTime;

        String[] items = new String[]{"Liana", "Will", "You", "Marry", "Me?!", "pls"};
        //SE PUEDE CAMBIAR EL TIPO DE DISEÑO DEL SPINER HERE! TODO
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                // HACER EL .set del ESTADO al ITEM SI SE QUIERE GUARDAR EL CAMBIO
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
            //TODO ESTADO

            tvDetalleCliente.setText("CLIENTE: " + client.getName());
            tvDetalleCalle.setText("CALLE: " + client.getStreet());
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

        btnDetalleCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityDetalleTicket.this, R.string.toast_cancelar_detalle_ticket, Toast.LENGTH_SHORT).show();
            }
        });

        btnDetalleGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityDetalleTicket.this, R.string.toast_guardar_detalle_ticket, Toast.LENGTH_SHORT).show();
            }
        });
    }


}