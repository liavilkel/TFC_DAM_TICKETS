package com.example.tfc_dam_tickets.persistence;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.tfc_dam_tickets.model.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TicketPersistence {

    static final String TABLA = "resolver_rocket.Tickets";
    static final String CAT = "cat_id";

    DBConnection DBCon;
    String conRes;
    Context context;

    public TicketPersistence(Context context) {
        this.context = context;
        DBCon = new DBConnection();
    }

    public ArrayList<Ticket> getTicketsByCat(int cat) {
        ArrayList<Ticket> tickets = new ArrayList<>();
        String query = "SELECT * FROM " + TABLA + " WHERE " + CAT +  " = ?";

        try (Connection connection = DBCon.getConnection();
             PreparedStatement stmt = connection != null ? connection.prepareStatement(query) : null) {
            if (stmt != null) {
                stmt.setInt(1, cat);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Ticket ticket = new Ticket(
                                rs.getLong("ticket_id"),
                                rs.getLong("cat_id"),
                                rs.getLong("client_id"),
                                rs.getString("user_open"),
                                rs.getString("user_close"),
                                rs.getString("title"),
                                rs.getString("description"),
                                rs.getString("status"),
                                rs.getString("solution"),
                                toLocalDateTime(rs.getTimestamp("ts_open")),
                                toLocalDateTime(rs.getTimestamp("ts_close"))
                        );
                        tickets.add(ticket);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp.getTime()), ZoneId.systemDefault());
    }
}