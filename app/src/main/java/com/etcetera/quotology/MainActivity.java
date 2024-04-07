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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import io.reactivex.rxjava3.annotations.NonNull;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerViewQuotes;
    private FirebaseRecyclerAdapter<Quote, QuoteViewHolder> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewQuotes = findViewById(R.id.recyclerViewQuotes);
        recyclerViewQuotes.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference quotesRef = FirebaseDatabase.getInstance().getReference("quotes");

        FirebaseRecyclerOptions<Quote> options =
                new FirebaseRecyclerOptions.Builder<Quote>()
                        .setQuery(quotesRef, Quote.class)
                        .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Quote, QuoteViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull QuoteViewHolder holder, int position, @NonNull Quote model) {
                holder.setQuote(model.getQuote());
                holder.setAuthor(model.getAuthor());
            }

            @Override
            public QuoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.activity_add_quote, parent, false);

                return new QuoteViewHolder(view);
            }
        };

        recyclerViewQuotes.setAdapter(firebaseRecyclerAdapter);

        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddQuoteActivity.class);
                startActivity(intent);
            }
        });
    }

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
