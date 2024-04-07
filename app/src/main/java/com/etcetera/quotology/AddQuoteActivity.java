package com.etcetera.quotology;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddQuoteActivity extends AppCompatActivity {

    DatabaseReference quotesRef;
    private EditText quoteEditText;
    private EditText authorEditText;
    private Button addButton;
    private RecyclerView recyclerView;
    private QuoteAdapter quoteAdapter;
    private List<Quote> quotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quote);

        //bind views
        quoteEditText = (EditText) findViewById(R.id.editTextQuote);
        authorEditText = (EditText) findViewById(R.id.editTextAuthor);
        addButton = (Button) findViewById(R.id.addButton);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerViewQuotes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the list of quotes
        quotes = new ArrayList<>();

        // Initialize the adapter with the empty list
        quoteAdapter = new QuoteAdapter(quotes);
        recyclerView.setAdapter(quoteAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get text
                String quote = quoteEditText.getText().toString();
                String author = authorEditText.getText().toString();

                //check if empty
                if (quote.isEmpty()){
                    quoteEditText.setError("Cannot be empty");
                    return;
                }
                if (author.isEmpty()){
                    authorEditText.setError("Cannot be empty");
                    return;
                }

                //add to database
                addQuoteToDB(quote,author);
            }
        });
    }

    private void addQuoteToDB(String quote, String author) {
        //create a hashmap
        HashMap<String, Object> quoteHashmap = new HashMap<>();
        quoteHashmap.put("quote",quote);
        quoteHashmap.put("author",author);

        //instantiate database connection
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference quotesRef = database.getReference("quotes");

        String key = quotesRef.push().getKey();
        quoteHashmap.put("key",key);

        quotesRef.child(key).setValue(quoteHashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(AddQuoteActivity.this, "Added", Toast.LENGTH_SHORT).show();
                quoteEditText.getText().clear();
                authorEditText.getText().clear();
            }
        });

        // Read data from the database
        quotesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called every time the data changes
                if (dataSnapshot.exists()) {
                    quotes.clear(); // Clear the list before adding new items
                    for (DataSnapshot quoteSnapshot : dataSnapshot.getChildren()) {
                        HashMap<String, Object> quoteMap = (HashMap<String, Object>) quoteSnapshot.getValue();
                        quotes.add(new Quote(quoteMap.get("quote").toString(), quoteMap.get("author").toString()));
                    }
                    quoteAdapter.notifyDataSetChanged(); // Notify the adapter that the data has changed
                } else {
                    // Handle the case where there are no quotes available
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // This method is called if an error occurs while
            }
        });
    }
}