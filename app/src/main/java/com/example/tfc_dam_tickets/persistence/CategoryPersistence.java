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
    static final String ORDER = "orderr";

    static final String NOMBRE = "name";
    static final String ACTIVE = "active";
    static final String CAT_ID = "cat_id";

    DBConnection DBCon;
    Context context; // Agregar contexto

    public CategoryPersistence(Context context) {
        this.context = context; // Inicializar contexto
        DBCon = new DBConnection();
    }

    public List<Category> getCategoryByPermission(List<Integer> allowedCategoryIds) {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM " + TABLA + " WHERE " + CAT_ID + " IN (";

        for (int i = 0; i < allowedCategoryIds.size(); i++) {
            if (i > 0) {
                query += ",";
            }
            query += "?";
        }
        query += ") ORDER BY " + ORDER;

        try (Connection connection = DBCon.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            for (int i = 0; i < allowedCategoryIds.size(); i++) {
                stmt.setInt(i + 1, allowedCategoryIds.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int catId = rs.getInt(CAT_ID);
                    int order = rs.getInt(ORDER);
                    String name = rs.getString(NOMBRE);
                    long active = rs.getLong(ACTIVE);

                    Category category = new Category(catId, order, name, active);
                    categories.add(category);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

}