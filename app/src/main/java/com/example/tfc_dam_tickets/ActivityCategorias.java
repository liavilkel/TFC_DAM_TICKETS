package com.example.tfc_dam_tickets;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.tfc_dam_tickets.adapterUtils.AdapterCategorias;
import com.example.tfc_dam_tickets.autenticacion.Login;
import com.example.tfc_dam_tickets.model.Category;
import com.example.tfc_dam_tickets.persistence.CategoryPersistence;
import com.example.tfc_dam_tickets.persistence.PermissionPersistence;

import java.util.List;

public class ActivityCategorias extends BaseActivity{

    RecyclerView rvCategorias;
    AdapterCategorias adapterCategorias;
    CategoryPersistence categoryPersistence;
    PermissionPersistence permissionPersistence;
    TextView tvHeader;

    List<Category> categorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);

        Toolbar customToolbar = findViewById(R.id.custom_actionbar);
        setSupportActionBar(customToolbar);

        rvCategorias = findViewById(R.id.rvCategorias);
        tvHeader = findViewById(R.id.tvCategorias);

        // Configurar GridLayoutManager
        int numberOfColumns = 2;  // Define el número de columnas que deseas en la cuadrícula
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, numberOfColumns);
        rvCategorias.setLayoutManager(gridLayoutManager);

        // Inicializar persistencias
        categoryPersistence = new CategoryPersistence(this);
        permissionPersistence = new PermissionPersistence(this);

        // Obtener categorías desde la base de datos
        Intent i = getIntent();
        if (i.hasExtra("email")) {
            String email = i.getStringExtra("email");
            List<Integer> allowedCategoryIds = permissionPersistence.getCategoryIdsByPermission(email);
            categorias = categoryPersistence.getCategoryByPermission(allowedCategoryIds);
        }

        if (categorias.isEmpty()) {
            tvHeader.setText("Se están gestionando los permisos para su usuario. En un plazo de 24h tendrá acceso a la aplicación.");
        }

        adapterCategorias = new AdapterCategorias(categorias, this, i.getStringExtra("email"));

        // Configurar RecyclerView con el adaptador
        rvCategorias.setAdapter(adapterCategorias);

        initializeUI();
    }


    private void initializeUI() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24);
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setTitle(R.string.tv_titulo_categorias);
        }
    }

}
