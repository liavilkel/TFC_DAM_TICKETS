package com.example.tfc_dam_tickets;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tfc_dam_tickets.model.Client;
import com.example.tfc_dam_tickets.model.User;
import com.example.tfc_dam_tickets.persistence.ClientPersistence;
import com.example.tfc_dam_tickets.persistence.UserPersistence;
import com.example.tfc_dam_tickets.utils.SessionManager;

import java.util.Locale;

public class ActivityDetalleUsuario extends BaseActivity {

    TextView tvEmail, tvNombre, tvApellidos, tvTelf, tvTipo, tvIdClient, tvClientName,
            tvClientStreet, tvClientProv, tvClientMun, tvClientZip;
    Button btnChangePw, btnSpanish, btnEnglish;
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

        btnSpanish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocale("es");
            }
        });

        btnEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocale("en");
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
        btnSpanish = findViewById(R.id.btnSpanish);
        btnEnglish = findViewById(R.id.btnEnglish);

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

    private void initializeUI() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.tv_titulo_datos_usuario);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuItem mnDatosItem = menu.findItem(R.id.mnDatos);
        if (mnDatosItem != null) {
            mnDatosItem.setEnabled(false);
            mnDatosItem.setVisible(false);
        }
        return true;
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());

        // Save the language setting to SharedPreferences
        SharedPreferences.Editor editor = getSharedPreferences("UserSettings_" + SessionManager.getLoggedInUserEmail(), MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();

        // Restart the activity to apply the language change
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}
