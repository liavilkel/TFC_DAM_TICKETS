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
    static final String TICKET_ID = "ticket_id";
    static final String USER_OPEN = "user_open";
    static final String USER_CLOSE = "user_close";
    static final String CAT = "cat_id";
    static final String CLIENT = "client_id";
    static final String TITLE = "title";
    static final String DESC = "description";
    static final String TS_OPEN = "ts_open";
    static final String TS_CLOSE = "ts_close";
    static final String STATUS = "status";
    static final String SOLUTION = "solution";

    DBConnection DBCon;
    Context context;

    public TicketPersistence(Context context) {
        this.context = context;
        DBCon = new DBConnection();
    }

    public Ticket getTicketById(Long id) {
        Ticket ticket = null;
        String query = "SELECT * FROM " + TABLA + " WHERE " + TICKET_ID +  " = ?";

        try (Connection connection = DBCon.getConnection();
             PreparedStatement stmt = connection != null ? connection.prepareStatement(query) : null) {
            if (stmt != null) {
                stmt.setLong(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {  // Change this to `if` since we expect only one result
                        ticket = new Ticket(
                                rs.getLong(TICKET_ID),
                                rs.getLong(CAT),
                                rs.getLong(CLIENT),
                                rs.getString(USER_OPEN),
                                rs.getString(USER_CLOSE),
                                rs.getString(TITLE),
                                rs.getString(DESC),
                                rs.getString(STATUS),
                                rs.getString(SOLUTION),
                                toLocalDateTime(rs.getTimestamp(TS_OPEN)),
                                toLocalDateTime(rs.getTimestamp(TS_CLOSE))
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticket;
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
                                rs.getLong(TICKET_ID),
                                rs.getLong(CAT),
                                rs.getLong(CLIENT),
                                rs.getString(USER_OPEN),
                                rs.getString(USER_CLOSE),
                                rs.getString(TITLE),
                                rs.getString(DESC),
                                rs.getString(STATUS),
                                rs.getString(SOLUTION),
                                toLocalDateTime(rs.getTimestamp(TS_OPEN)),
                                toLocalDateTime(rs.getTimestamp(TS_CLOSE))
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

    public byte postNewTicket(String userOpen, Long catId, Long clientId, String title, String desc, String status) {
        byte response = 0;
        String query = "INSERT INTO " + TABLA + " (" + USER_OPEN + "," + USER_CLOSE + ", " + CAT + ", " + CLIENT + ", "
                +  TITLE + ", " + DESC + ", " + TS_OPEN + ", " + TS_CLOSE + ", " + STATUS + ", " + SOLUTION
                + ") VALUES (?, NULL, ?, ?, ?, ?, NOW(), NULL, ?, NULL)";

        try (Connection connection = DBCon.getConnection();
             PreparedStatement stmt = connection != null ? connection.prepareStatement(query) : null) {
            if (stmt != null) {
                stmt.setString(1, userOpen);
                stmt.setLong(2, catId);
                stmt.setLong(3, clientId);
                stmt.setString(4, title);
                stmt.setString(5, desc);
                stmt.setString(6, status);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    response = 1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return response;
    }


    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp != null) {
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp.getTime()), ZoneId.systemDefault());
        } else {
            return null; // or any other default value you prefer
        }
    }

}