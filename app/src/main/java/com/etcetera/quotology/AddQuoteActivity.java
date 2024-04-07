package com.etcetera.quotology;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AddQuoteActivity extends AppCompatActivity {

    DatabaseReference quotesRef;
    private EditText quoteEditText;
    private EditText authorEditText;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quote);

        //bind views
        quoteEditText = (EditText) findViewById(R.id.editTextQuote);
        authorEditText = (EditText) findViewById(R.id.editTextAuthor);
        addButton = (Button) findViewById(R.id.addButton);






        /*
        //listener
        quotesRef = FirebaseDatabase.getInstance().getReference();
        quotesRef.child("quotes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String author = dataSnapshot.getValue().toString();
                    mTextViewData.setText("El nombre es:" +author);
                }
            });
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Log.e("Datos:", ""+snapshot.getValue());
                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        }*/


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


        //create in database

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

        // Lee datos de la base de datos
        quotesRef.addValueEventListener(new ValueEventListener() {
            TextView mTextViewData = (TextView) findViewById(R.id.dato);
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Este método se llama cada vez que los datos cambian
                if (dataSnapshot.exists()) {
                    StringBuilder quotesData = new StringBuilder();
                    for (DataSnapshot quoteSnapshot : dataSnapshot.getChildren()) {
                        HashMap<String, Object> quoteMap = (HashMap<String, Object>) quoteSnapshot.getValue();
                        quotesData.append("Cita: ").append(quoteMap.get("quote")).append("\n");
                        quotesData.append("Autor: ").append(quoteMap.get("author")).append("\n\n");
                    }
                    TextView mTextViewData = findViewById(R.id.dato);
                    mTextViewData.setText(quotesData.toString());
                } else {
                    TextView mTextViewData = findViewById(R.id
                            .dato);
                    mTextViewData.setText("No hay citas disponibles.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Este método se llama si ocurre un error al leer los datos
                TextView mTextViewData = findViewById(R.id.dato);
                mTextViewData.setText("Error al leer los datos.");
            }
        });

    }
}