package com.etcetera.quotology;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.List;

public class QuoteAdapter extends RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder> {

    private List<Quote> quotes;

    // Constructor que toma la lista de citas
    public QuoteAdapter(List<Quote> quotes) {
        this.quotes = quotes;
    }

    // Crea nuevos ViewHolders
    @NonNull
    @Override
    public QuoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_quote_adapter, parent, false);
        return new QuoteViewHolder(view);
    }

    // Enlaza los datos a los ViewHolders
    @Override
    public void onBindViewHolder(@NonNull QuoteViewHolder holder, int position) {
        Quote quote = quotes.get(position);
        holder.quoteTextView.setText(quote.getQuote());
        holder.authorTextView.setText(quote.getAuthor());
    }

    // Devuelve el número total de elementos en la lista
    @Override
    public int getItemCount() {
        return quotes.size();
    }

    // ViewHolder para los elementos de la lista
    public static class QuoteViewHolder extends RecyclerView.ViewHolder {
        TextView quoteTextView, authorTextView;

        public QuoteViewHolder(@NonNull View itemView) {
            super(itemView);
            quoteTextView = itemView.findViewById(R.id.quoteTextView);
            authorTextView = itemView.findViewById(R.id.authorTextView);
        }
    }
}
