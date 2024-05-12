package com.example.tfc_dam_tickets.adapterUtils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfc_dam_tickets.ActivityCategorias;
import com.example.tfc_dam_tickets.TicketsList;
import com.example.tfc_dam_tickets.model.Category;
import com.example.tfc_dam_tickets.R;
import java.util.List;

public class AdapterCategorias extends RecyclerView.Adapter<AdapterCategorias.ViewHolder> {
    private List<Category> categorias;
    Context context;

    public AdapterCategorias(List<Category> categorias, Context context) {
        this.categorias = categorias;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_categoria, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category categoria = categorias.get(position);
        holder.tvNomCategoria.setText(categoria.getName());

        //cuando pulsamos a un item
        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Category activityCategorias = categorias.get(holder.getAdapterPosition());
                Intent i = new Intent(context, TicketsList.class);
                context.startActivity(i);


            }
        });
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNomCategoria;
        CardView recCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNomCategoria = itemView.findViewById(R.id.tv_nom_categoria);
            recCard = itemView.findViewById(R.id.recCard);
        }
    }
}
