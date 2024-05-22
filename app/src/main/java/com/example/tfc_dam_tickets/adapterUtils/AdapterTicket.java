package com.example.tfc_dam_tickets.adapterUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfc_dam_tickets.R;
import com.example.tfc_dam_tickets.model.Ticket;
import com.example.tfc_dam_tickets.persistence.ClientPersistence;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AdapterTicket extends RecyclerView.Adapter<AdapterTicket.TicketViewHolder> {

    private ArrayList<Ticket> ticketsList;
    ClientPersistence clientPersistence;
    Context context;

    public AdapterTicket(Context context, ArrayList<Ticket> ticketsList) {
        this.context = context;
        this.ticketsList = ticketsList;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context)
                .inflate(R.layout.ticket_item, parent, false);

        return new TicketViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {

        clientPersistence = new ClientPersistence();

        Ticket ticket = ticketsList.get(position);
        holder.tvIdTicket.setText(String.valueOf(ticket.getTicketId()));

        holder.tvIdTicket.setText("Id: " + String.valueOf(ticket.getTicketId()));
        holder.tvCliente.setText(clientPersistence.getNameById(ticket.getClientId()));
        holder.tvEstado.setText(ticket.getStatus());
        holder.tvTitulo.setText(ticket.getTitle());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = ticket.getTsOpen().format(formatter);
        holder.tvFecha.setText(formattedDate);
        //holder.tvFecha.setText(String.valueOf(ticket.getTsOpen().getMonth()));

        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime = ticket.getTsOpen().format(formatter2);
        holder.tvHora.setText(formattedTime);
        //holder.tvHora.setText(String.valueOf(ticket.getTsOpen().getHour()));

    }

    @Override
    public int getItemCount() {
        return ticketsList.size();
    }

    public static class TicketViewHolder extends RecyclerView.ViewHolder {
        public TextView tvIdTicket;
        public TextView tvFecha;
        public TextView tvHora;
        public TextView tvCliente;
        public TextView tvEstado;
        public TextView tvTitulo;

        public TicketViewHolder(View view) {
            super(view);
            tvIdTicket = view.findViewById(R.id.tvIdTicket);
            tvFecha = view.findViewById(R.id.tvFecha);
            tvHora = view.findViewById(R.id.tvHora);
            tvCliente = view.findViewById(R.id.tvCliente);
            tvEstado = view.findViewById(R.id.tvEstado);
            tvTitulo = view.findViewById(R.id.tvTitulo);
            //hola
        }
    }
}
