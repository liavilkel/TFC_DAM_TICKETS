package com.example.tfc_dam_tickets.persistence;

import android.content.Context;

import com.example.tfc_dam_tickets.model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryPersistence {

    static final String TABLA = "resolver_rocket.Categories "; // Cambiar por el nombre de tu tabla de categorías
    static final String ORDER = "orderr";

    static final String NAME = "name";
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
                    String name = rs.getString(NAME);
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

    public String getNameById(Integer id) {

        String query = "SELECT " + NAME + " FROM " + TABLA + " WHERE " + CAT_ID + " =?";
        try (Connection connection = DBCon.getConnection();
             PreparedStatement stmt = connection != null ? connection.prepareStatement(query) : null) {

            if (stmt != null){
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("name");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "notFound";
    }

}