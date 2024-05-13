package com.example.tfc_dam_tickets.persistence;

import android.content.Context;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PermissionPersistence {
    static final String TABLA = "resolver_rocket.Permissions";
    static final String USER = "user_email";
    static final String CAT = "category_id";

    DBConnection DBCon;
    Context context;

    public PermissionPersistence(Context context) {
        this.context = context;
        DBCon = new DBConnection();
    }

    public List<Integer> getCategoryIdsByPermission(String email) {
        List<Integer> categoryIds = new ArrayList<>();
        // Query to retrieve category IDs based on user permissions
        String query = "SELECT " + CAT + " FROM " + TABLA + " WHERE " + USER + " = ?";

        try (Connection connection = DBCon.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    categoryIds.add(rs.getInt(CAT));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryIds;
    }

}
