package com.etcetera.quotology;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class SearchQuoteActivity extends AppCompatActivity {

    private EditText searchEditText;
    private RecyclerView searchRecyclerView;
    private QuoteAdapter quoteAdapter;
    private List<Quote> entregable4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_quote);

        searchEditText = findViewById(R.id.searchEditText);
        searchRecyclerView = findViewById(R.id.searchRecyclerView);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        entregable4 = new ArrayList<>();
        quoteAdapter = new QuoteAdapter(entregable4);
        searchRecyclerView.setAdapter(quoteAdapter);


        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                searchentregable4(s.toString());
            }
        });
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Esto llama al método onBackPressed() de la actividad, que por defecto cierra la actividad actual y vuelve a la anterior.
            }
        });
    }

    private void searchentregable4(String query) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference entregable4Ref = database.getReference("entregable4");

        // Realiza las consultas tanto para 'quote' como para 'author'
        Query quoteQuery = entregable4Ref.orderByChild("quote").startAt(query).endAt(query + "\uf8ff");
        Query authorQuery = entregable4Ref.orderByChild("author").startAt(query).endAt(query + "\uf8ff");

        // Inicializa una lista para almacenar los resultados combinados
        final List<Quote> combinedResults = new ArrayList<>();

        ValueEventListener quoteListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Quote> quoteResults = new ArrayList<>();
                for (DataSnapshot entregable4napshot : dataSnapshot.getChildren()) {
                    HashMap<String, Object> quoteMap = (HashMap<String, Object>) entregable4napshot.getValue();
                    quoteResults.add(new Quote(quoteMap.get("quote").toString(), quoteMap.get("author").toString()));
                }
                // Agrega los resultados de la consulta por 'quote' a la lista combinada
                combinedResults.addAll(quoteResults);
                // Combina y muestra los resultados
                displaySearchResults(combinedResults);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        };

        ValueEventListener authorListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Quote> authorResults = new ArrayList<>();
                for (DataSnapshot authorSnapshot : dataSnapshot.getChildren()) {
                    HashMap<String, Object> authorMap = (HashMap<String, Object>) authorSnapshot.getValue();
                    authorResults.add(new Quote(authorMap.get("quote").toString(), authorMap.get("author").toString()));
                }
                // Agrega los resultados de la consulta por 'author' a la lista combinada
                combinedResults.addAll(authorResults);
                // Combina y muestra los resultados
                displaySearchResults(combinedResults);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        };

        // Agrega los listeners a las consultas
        quoteQuery.addValueEventListener(quoteListener);
        authorQuery.addValueEventListener(authorListener);
    }

    private void displaySearchResults(List<Quote> results) {
        entregable4.clear(); // Limpiar la lista actual de citas
        entregable4.addAll(results); // Agregar los resultados combinados
        quoteAdapter.notifyDataSetChanged(); // Notificar al adaptador del cambio en los datos
    }

}
