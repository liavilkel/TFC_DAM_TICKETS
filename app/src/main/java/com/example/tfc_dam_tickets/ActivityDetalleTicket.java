package com.example.tfc_dam_tickets;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_ticket);

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