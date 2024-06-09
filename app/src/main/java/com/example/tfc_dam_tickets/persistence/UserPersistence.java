package com.example.tfc_dam_tickets.persistence;

import android.content.Context;
import android.util.Log;

import com.example.tfc_dam_tickets.model.User;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserPersistence {

    static final String TABLA = "resolver_rocket.Users";
    static final String EMAIL = "email";
    static final String CONT = "password";
    static final String NOMBRE = "name";
    static final String APE = "last_name";
    static final String TELF = "phone_number";
    static final String TIPO = "type";
    static final String CLIENT_ID = "client_id";
    static final String REC_CODE = "rec_code";

    DBConnection DBCon;
    Context context;

    public UserPersistence(Context context) {
        this.context = context; // Initialize context
        DBCon = new DBConnection();
    }

    public int newUser(User user) {
        String query = "INSERT INTO " + TABLA
                + " ( " + EMAIL + ", " + CONT + ", " + NOMBRE + ", " + APE + ", " + TELF + ", " + TIPO
                + ", " + CLIENT_ID + ", " + REC_CODE + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        int res = 0;

        try (Connection connection = DBCon.getConnection();
             PreparedStatement stmt = connection != null ? connection.prepareStatement(query) : null) {

            if (stmt != null) {

                String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

                stmt.setString(1, user.getEmail());
                stmt.setString(2, hashedPassword);
                stmt.setString(3, user.getName());
                stmt.setString(4, user.getLastName());
                stmt.setString(5, user.getPhoneNum());
                stmt.setString(6, user.getType());
                stmt.setLong(7, user.getComId());
                stmt.setString(8, user.getRecCode());

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
        try (Connection connection = DBCon.getConnection();
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

    public boolean isEmailInUse(String email) {
        String query = "SELECT COUNT(*) FROM " + TABLA + " WHERE " + EMAIL + " = ?";
        int count = 0;

        try (Connection connection = DBCon.getConnection();
             PreparedStatement stmt = connection != null ? connection.prepareStatement(query) : null) {

            if (stmt != null) {
                stmt.setString(1, email);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        count = rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count > 0;
    }

    public Long getClientIdByEmail(String userOpen) {

        String query = "SELECT " + CLIENT_ID + " FROM " + TABLA + " WHERE " + EMAIL + " =?";
        Long id = -1L;

        try (Connection connection = DBCon.getConnection();
             PreparedStatement stmt = connection != null ? connection.prepareStatement(query) : null) {

            if (stmt != null) {
                stmt.setString(1, userOpen);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        id = rs.getLong(CLIENT_ID);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public User getUserByEmail(String email) {
        User user = null;
        String query = "SELECT " + EMAIL + ", " + NOMBRE + ", " + APE + ", " + TELF + ", " + TIPO + ", " + CLIENT_ID +
                " FROM " + TABLA + " WHERE " + EMAIL + " = ?";

        try (Connection connection = DBCon.getConnection();
             PreparedStatement stmt = connection != null ? connection.prepareStatement(query) : null) {

            if (stmt != null) {
                stmt.setString(1, email);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {  // Expecting only one result for a unique email
                        user = new User(
                                rs.getString(EMAIL),
                                null, // Exclude password
                                rs.getString(NOMBRE),
                                rs.getString(APE),
                                rs.getString(TELF),
                                rs.getString(TIPO),
                                rs.getLong(CLIENT_ID)
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }


    public String getUserRecCode(String email) {

        String query = "SELECT " + REC_CODE + " FROM " + TABLA + " WHERE " + EMAIL + " =?";
        String code = null;

        try (Connection connection = DBCon.getConnection();
             PreparedStatement stmt = connection != null ? connection.prepareStatement(query) : null) {

            if (stmt != null) {
                stmt.setString(1, email);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        code = rs.getString(REC_CODE);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return code;

    }

    public boolean updatePasswordAndRecoveryCode(String email, String newPassword, String newRecoveryCode) {
        String query = "UPDATE " + TABLA + " SET " + CONT + " = ?, " + REC_CODE + " = ? WHERE " + EMAIL + " = ?";
        boolean success = false;

        try (Connection connection = DBCon.getConnection();
             PreparedStatement stmt = connection != null ? connection.prepareStatement(query) : null) {

            if (stmt != null) {
                stmt.setString(1, newPassword);
                stmt.setString(2, newRecoveryCode);
                stmt.setString(3, email);
                int affectedRows = stmt.executeUpdate();
                success = affectedRows > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    public List<User> getTecUsers() {
        List<User> adminUsers = new ArrayList<>();
        String query = "SELECT * FROM " + TABLA + " WHERE " + TIPO + " = 'tecnico'";

        try (Connection connection = DBCon.getConnection();
             PreparedStatement stmt = connection != null ? connection.prepareStatement(query) : null) {
            if (stmt != null) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        User user = new User(
                                rs.getString(EMAIL),
                                rs.getString("password"),
                                rs.getString(NOMBRE),
                                rs.getString(APE),
                                rs.getString(TELF),
                                rs.getString(TIPO),
                                rs.getLong(CLIENT_ID)
                        );
                        adminUsers.add(user);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adminUsers;
    }



}
