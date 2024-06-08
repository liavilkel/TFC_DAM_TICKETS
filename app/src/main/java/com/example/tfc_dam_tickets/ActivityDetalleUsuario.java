package com.example.tfc_dam_tickets;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tfc_dam_tickets.autenticacion.Login;
import com.example.tfc_dam_tickets.model.Client;
import com.example.tfc_dam_tickets.model.User;
import com.example.tfc_dam_tickets.persistence.ClientPersistence;
import com.example.tfc_dam_tickets.persistence.UserPersistence;
import com.example.tfc_dam_tickets.utils.SessionManager;

public class ActivityDetalleUsuario extends AppCompatActivity {

    TextView tvEmail, tvNombre, tvApellidos, tvTelf, tvTipo, tvIdClient, tvClientName,
            tvClientStreet, tvClientProv, tvClientMun, tvClientZip;
    Button btnChangePw;
    UserPersistence userPersistence;
    ClientPersistence clientPersistence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_usuario);

        userPersistence = new UserPersistence(this);
        clientPersistence = new ClientPersistence(this);


        User user = userPersistence.getUserByEmail(SessionManager.getLoggedInUserEmail());
        Client client = clientPersistence.findClientById(user.getComId());

        initActionBar();
        initFields();
        fillInFields(user, client);

        btnChangePw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActivityDetalleUsuario.this, SetNewPasswordActivity.class);
                i.putExtra("email", user.getEmail());
                i.putExtra("outcome", "userInfo");
                startActivity(i);
            }
        });

    }

    private void initFields() {
        tvEmail = findViewById(R.id.tvDatosEmail);
        tvNombre = findViewById(R.id.tvDatosNombre);
        tvApellidos = findViewById(R.id.tvDatosApe);
        tvTelf = findViewById(R.id.tvDatosTelef);
        tvTipo = findViewById(R.id.tvDatosTipo);

        tvIdClient = findViewById(R.id.tvDatosIdEmpresa);
        tvClientName = findViewById(R.id.tvDatosNombreEmp);
        tvClientStreet = findViewById(R.id.tvDatosCalleEmpresa);
        tvClientProv = findViewById(R.id.tvDatosProvinciaEmpresa);
        tvClientMun = findViewById(R.id.tvDatosMunicipioEmpresa);
        tvClientZip = findViewById(R.id.tvDatosZipEmpresa);

        btnChangePw = findViewById(R.id.btnCambiarPw);

    }

    private void fillInFields(User user, Client client) {

        tvEmail.setText(user.getEmail());
        tvNombre.setText(user.getName());
        tvApellidos.setText(user.getLastName());
        tvTelf.setText(user.getPhoneNum());
        tvTipo.setText(user.getType());

        tvIdClient.setText(client.getClientId().toString());
        tvClientName.setText(client.getName());
        tvClientStreet.setText(client.getStreet());
        tvClientProv.setText(client.getProvince());
        tvClientMun.setText(client.getMunicipality());
        tvClientZip.setText(client.getZipCode());

    }

    private void initActionBar() {
        Toolbar customToolbar = findViewById(R.id.custom_actionbar);
        setSupportActionBar(customToolbar);
        initializeUI();
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
            actionBar.setTitle(R.string.tv_titulo_datos_usuario);
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

                    Intent intent = new Intent(ActivityDetalleUsuario.this, Login.class);
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
}