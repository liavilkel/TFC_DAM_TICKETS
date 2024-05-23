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
import androidx.appcompat.widget.Toolbar;

import com.example.tfc_dam_tickets.adapterUtils.AdapterCategorias;
import com.example.tfc_dam_tickets.autenticacion.Login;
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

        Toolbar customToolbar = findViewById(R.id.custom_actionbar);
        setSupportActionBar(customToolbar);

        // Inicializar RecyclerView
        rvCategorias = findViewById(R.id.rvCategorias);

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

        adapterCategorias = new AdapterCategorias(categorias, this, i.getStringExtra("email"));

        // Configurar RecyclerView con el adaptador
        rvCategorias.setAdapter(adapterCategorias);

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
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setTitle(R.string.tv_titulo_categorias);
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

                    Intent intent = new Intent(ActivityCategorias.this, Login.class);
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
