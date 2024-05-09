package com.example.tfc_dam_tickets.persistence;

import com.example.tfc_dam_tickets.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserPersistence {

    static final String TABLA = "resolver_rocker.Users"; //resolver_rocker.Users
    static final String EMAIL = "email";
    static final String CONT = "password";
    static final String NOMBRE = "name";
    static final String APE = "last_name";
    static final String TELF = "phone_number";
    static final String TIPO = "type";

    DBContection con;

    public UserPersistence() {
        con = new DBContection();
    }

    public int  newUser(User user) {

        String query = "INSERT INTO " + TABLA
                + " ( " + EMAIL + ", " + CONT + ", " + NOMBRE + ", " + APE + ", " + TELF + ", " + TIPO +
                ") VALUES (?, ?, ?, ?, ?, ?";

        int res = 0;
        Connection connection = null;
        PreparedStatement stmt = null;

        try {

            connection = con.getConection();
            stmt = connection.prepareStatement(query);

            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getName());
            stmt.setString(4, user.getLastName());
            stmt.setString(5, user.getPhone_num());
            stmt.setString(6, user.getType());

        } catch (Exception e) {
            e.printStackTrace();
            res = -1;
        } finally {
            try {
                if (stmt != null)
                    stmt.close();

               // if (con != null)
                    //connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return res;

    }

}
