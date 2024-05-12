package com.example.tfc_dam_tickets.persistence;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.tfc_dam_tickets.model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CategoryPersistence {

    static final String TABLA = "resolver_rocket.Categories "; // Cambiar por el nombre de tu tabla de categorías
    static final String ORDER = "order";
    static final String NOMBRE = "name";
    static final String ACTIVE = "active";
    static final String CAT_ID = "cat_id";

    DBConnection DBCon;
    String conRes;
    Context context; // Agregar contexto

    public CategoryPersistence(Context context) {
        this.context = context; // Inicializar contexto
        DBCon = new DBConnection();
    }

    public List<Category> obtenerCategorias() {
        List<Category> categorias = new ArrayList<>();
        String query = "SELECT * FROM " + TABLA + " ORDER BY " + NOMBRE;

        try (Connection connection = DBCon.getConection();
             PreparedStatement stmt = connection != null ? connection.prepareStatement(query) : null) {

            if (stmt != null) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        int catId = rs.getInt(CAT_ID);
                        int order = rs.getInt(ORDER);
                        String name = rs.getString(NOMBRE);
                        long active = rs.getLong(ACTIVE);

                        Category categoria = new Category(catId, order, name, active);
                        categorias.add(categoria);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categorias;
    }

    public void conectar() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                try (Connection con = DBCon.getConection()) {
                    if (con == null) {
                        conRes = "No se puede conectar con el servidor";
                    } else {
                        conRes = "Conectado con el servidor";
                    }
                }
            } catch (Exception e) {
                conRes = "Error de conexión: " + e.getMessage();
            }

            // Usar Handler para mostrar Toast en el hilo principal
            new Handler(Looper.getMainLooper()).post(() -> {
                Toast.makeText(context, conRes, Toast.LENGTH_SHORT).show();
            });
        });
    }
}