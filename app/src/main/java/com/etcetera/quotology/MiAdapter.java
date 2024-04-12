package com.etcetera.quotology;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Adapter personalizado para el RecyclerView
public class MiAdapter extends RecyclerView.Adapter<MiAdapter.ViewHolder> {
    private List<Quote> datos;

    public MiAdapter(List<Quote> datos) {
        this.datos = datos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_quote, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Quote dato = datos.get(position);
        holder.bind(dato);
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView quote;
        TextView author;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            quote = itemView.findViewById(R.id.quote);
            author = itemView.findViewById(R.id.author);
        }

        public void bind(Quote dato) {
            quote.setText(dato.getQuote());
            author.setText(dato.getAuthor());
        }
    }
}