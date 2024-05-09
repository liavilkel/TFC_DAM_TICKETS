package com.example.tfc_dam_tickets.persistence;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    String username, password, ip, port, database;

    public Connection getConection(){
        ip = "82.223.204.116";
        database = "resolver_rocket";
        username = "tfg_user";
        password = "javi javi";
        port = "3306";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            Log.d("DBContection", "MySQL JDBC Driver loaded");

            // Construct the connection URL
            String connectionURL = "jdbc:mysql://" + ip + ":" + port + "/" + database + "?useSSL=false";

            Log.d("DBContection", "Connection URL: " + connectionURL);

            // Attempt to establish a connection
            connection = DriverManager.getConnection(connectionURL, username, password);
            Log.d("DBContection", "Connection established successfully");

        } catch (ClassNotFoundException e) {
            Log.e("DBContection Error", "MySQL JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            Log.e("DBContection Error", "SQL Exception: " + e.getMessage());
        }

        return connection;
    }
}
