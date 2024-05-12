package com.example.tfc_dam_tickets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import com.example.tfc_dam_tickets.adapterUtils.AdapterCategorias;
import com.example.tfc_dam_tickets.model.Category;
import com.example.tfc_dam_tickets.persistence.CategoryPersistence;
import java.util.List;

public class ActivityCategorias extends AppCompatActivity {

    RecyclerView rvCategorias;
    AdapterCategorias adapterCategorias;
    CategoryPersistence categoryPersistence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);

        // Inicializar RecyclerView
        rvCategorias = findViewById(R.id.rvCategorias);
        rvCategorias.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar persistencia de categorías
        categoryPersistence = new CategoryPersistence(this);

        // Obtener categorías desde la base de datos
        List<Category> categorias = categoryPersistence.obtenerCategorias();

        // Inicializar adaptador con las categorías obtenidas
        adapterCategorias = new AdapterCategorias(categorias);

        // Configurar RecyclerView con el adaptador
        rvCategorias.setAdapter(adapterCategorias);
    }
}
