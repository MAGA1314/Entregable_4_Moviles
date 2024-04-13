package com.etcetera.quotology;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton, searchActionButton;
    private RecyclerView recyclerViewentregable4;
    private QuoteAdapter quoteAdapter; // Asumiendo que QuoteAdapter es tu adaptador personalizado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //recyclerViewentregable4 = findViewById(R.id.recyclerViewentregable4);
        //recyclerViewentregable4.setLayoutManager(new LinearLayoutManager(this));

        // Obtener la lista de citas del Singleton
        List<Quote> entregable4 = QuoteData.getInstance().getentregable4();

        // Inicializar el adaptador con la lista de citas
        //quoteAdapter = new QuoteAdapter(entregable4);
        //recyclerViewentregable4.setAdapter(quoteAdapter);

        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddQuoteActivity.class);
                startActivity(intent);
            }
        });
        FloatingActionButton searchQuoteButton = findViewById(R.id.searchQuoteButton);
        searchQuoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchQuoteActivity.class);
                startActivity(intent);
            }
        });
    }



    // Asumiendo que tienes un ViewHolder personalizado para tu adaptador
    public static class QuoteViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public QuoteViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setQuote(String quote) {
            TextView quoteTextView = mView.findViewById(R.id.quoteTextView);
            quoteTextView.setText(quote);
        }

        public void setAuthor(String author) {
            TextView authorTextView = mView.findViewById(R.id.authorTextView);
            authorTextView.setText(author);
        }
    }
}
