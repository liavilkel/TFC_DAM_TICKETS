package com.example.tfc_dam_tickets;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.tfc_dam_tickets.adapterUtils.AdapterCategorias;
import com.example.tfc_dam_tickets.model.Category;
import com.example.tfc_dam_tickets.persistence.CategoryPersistence;
import com.example.tfc_dam_tickets.persistence.PermissionPersistence;

import java.util.List;

public class ActivityCategorias extends AppCompatActivity {

    RecyclerView rvCategorias;
    AdapterCategorias adapterCategorias;
    CategoryPersistence categoryPersistence;
    PermissionPersistence permissionPersistence;

    List<Category> categorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);
        initializeUI();

        // Inicializar RecyclerView
        rvCategorias = findViewById(R.id.rvCategorias);
        rvCategorias.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar persistencias
        categoryPersistence = new CategoryPersistence(this);
        permissionPersistence = new PermissionPersistence(this);

        // Obtener categorías desde la base de datos
        Intent i = getIntent();
        if (i.hasExtra("email")) {
            String email = i.getStringExtra("email");

            // Retrieve category IDs allowed for the user
            List<Integer> allowedCategoryIds = permissionPersistence.getCategoryIdsByPermission(email);

            // Retrieve categories based on the allowed category IDs
            categorias = categoryPersistence.getCategoryByPermission(allowedCategoryIds);
        }


        // Inicializar adaptador con las categorías obtenidas
        adapterCategorias = new AdapterCategorias(categorias, this);

        // Configurar RecyclerView con el adaptador
        rvCategorias.setAdapter(adapterCategorias);
    }

    //TODO MOSTRAR ACTION_BAR
    private void initializeUI() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.tv_titulo_categorias);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // ID del botón de retroceso en la ActionBar
        if (item.getItemId() == android.R.id.home) {
            // Finaliza la actividad actual para volver a MainActivity
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}