package com.example.tfc_dam_tickets.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientPersistence {

    static final String TABLA = "Clients";
    static final String CLIENT_ID = "client_id";
    static final String NAME = "name";

    DBConnection dbConnection;

    public ClientPersistence() {
        dbConnection = new DBConnection();
    }


    public boolean clientExists(String clientId) {
        String query = "SELECT COUNT(*) AS count FROM " + TABLA + " WHERE " + CLIENT_ID + " = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement stmt = connection != null ? connection.prepareStatement(query) : null) {

            if (stmt != null) {
                stmt.setString(1, clientId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int count = rs.getInt("count");
                        return count > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getNameById(Long id) {

        String query = "SELECT " + NAME + " FROM " + TABLA + " WHERE " + CLIENT_ID + " =?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement stmt = connection != null ? connection.prepareStatement(query) : null) {

            if (stmt != null){
                stmt.setLong(1, id);
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

