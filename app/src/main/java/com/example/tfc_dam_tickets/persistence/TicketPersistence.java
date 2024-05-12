package com.example.tfc_dam_tickets.persistence;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.tfc_dam_tickets.model.Ticket;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TicketPersistence {

    static final String TABLA = "resolver_rocket.Users";
    static final String ID_TICKET = "ticket_id";
    static final String USER_OPEN = "user_open";
    static final String USER_CLOSE = "user_close";
    static final String CAT_ID = "cat_ID";
    static final String CLIENT_ID = "client_id";
    static final String TITLE = "title";
    static final String DESC = "description";
    static final String TS_OPEN = "ts_open";
    static final String TS_CLOSE = "ts_close";
    static final String STATUS = "status";
    static final String SOLUTION = "solution";

    DBConnection DBCon;
    String conRes;
    Context context;

    public TicketPersistence(Context context) {
        this.context = context;
        DBCon = new DBConnection();
    }




    public ArrayList<Ticket>cargarTodosTickets(){

        ArrayList<Ticket> tickets = new ArrayList<>();



        return tickets;
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
