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

import com.example.tfc_dam_tickets.autenticacion.Login;

public class ActivityDetalleUsuario extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_usuario);

        //ACTION BAR
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


}