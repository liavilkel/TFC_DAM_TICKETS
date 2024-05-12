package com.example.tfc_dam_tickets.persistence;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.tfc_dam_tickets.model.User;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserPersistence {

    static final String TABLA = "resolver_rocket.Users";
    static final String EMAIL = "email";
    static final String CONT = "password";
    static final String NOMBRE = "name";
    static final String APE = "last_name";
    static final String TELF = "phone_number";
    static final String TIPO = "type";
    static final String CLIENT_ID = "client_id";

    DBConnection DBCon;
    String conRes;
    Context context; // Add context

    public UserPersistence(Context context) {
        this.context = context; // Initialize context
        DBCon = new DBConnection();
    }

    public int newUser(User user) {
        String query = "INSERT INTO " + TABLA
                + " ( " + EMAIL + ", " + CONT + ", " + NOMBRE + ", " + APE + ", " + TELF + ", " + TIPO +
                ") VALUES (?, ?, ?, ?, ?, ?)";

        int res = 0;

        try (Connection connection = DBCon.getConection();
             PreparedStatement stmt = connection != null ? connection.prepareStatement(query) : null) {

            if (stmt != null) {

                String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

                stmt.setString(1, user.getEmail());
                stmt.setString(2, hashedPassword);
                stmt.setString(3, user.getName());
                stmt.setString(4, user.getLastName());
                stmt.setString(5, user.getPhone_num());
                stmt.setString(6, user.getType());

                res = stmt.executeUpdate(); // Execute the insert statement
                Log.d("UserPersistence", "User inserted successfully");
            } else {
                res = -1;
                System.err.println("Failed to make connection!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res = -1;
        }
        return res;
    }

    public boolean verifyUser(String email, String plainPassword) {
        String query = "SELECT " + CONT + " FROM " + TABLA + " WHERE " + EMAIL + " = ?";
        try (Connection connection = DBCon.getConection();
             PreparedStatement stmt = connection != null ? connection.prepareStatement(query) : null) {

            if (stmt != null) {
                stmt.setString(1, email);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String storedHash = rs.getString(CONT);
                        return BCrypt.checkpw(plainPassword, storedHash);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void connect() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                try (Connection con = DBCon.getConection()) {
                    if (con == null) {
                        conRes = "Unable to connect with server";
                    } else {
                        conRes = "Connected with server";
                    }
                }
            } catch (Exception e) {
                conRes = "Connection failed: " + e.getMessage();
            }

            // Use Handler to show Toast on main thread
            new Handler(Looper.getMainLooper()).post(() -> {
                Toast.makeText(context, conRes, Toast.LENGTH_SHORT).show();
            });
        });
    }
}
