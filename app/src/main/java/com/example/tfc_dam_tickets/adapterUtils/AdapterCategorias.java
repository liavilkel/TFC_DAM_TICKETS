package com.example.tfc_dam_tickets.adapterUtils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tfc_dam_tickets.model.Category;
import com.example.tfc_dam_tickets.R;
import java.util.List;

public class AdapterCategorias extends RecyclerView.Adapter<AdapterCategorias.ViewHolder> {
    private List<Category> categorias;

    public AdapterCategorias(List<Category> categorias) {
        this.categorias = categorias;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categoria, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category categoria = categorias.get(position);
        holder.tvNomCategoria.setText(categoria.getName());
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNomCategoria;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNomCategoria = itemView.findViewById(R.id.tv_nom_categoria);
        }
    }
}
