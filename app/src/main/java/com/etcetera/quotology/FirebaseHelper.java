package com.etcetera.quotology;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

// Paso 4: Clase para manejar la consulta a Firebase
public class FirebaseHelper {
    private DatabaseReference mDatabase;

    public FirebaseHelper() {
        // Obtiene una referencia a la base de datos
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void obtenerDatos(final OnDataReceivedListener listener) {
        // Realiza la consulta a la base de datos
        Query query = mDatabase.child("quotes");

        // Agrega un listener para obtener los datos
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Quote> datos = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Quote dato = snapshot.getValue(Quote.class);
                    datos.add(dato);
                }
                // Notifica al listener que se han recibido los datos
                listener.onDataReceived(datos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Maneja los errores en la consulta
                Log.e("FirebaseHelper", "Error al obtener datos: " + databaseError.getMessage());
            }
        });
    }

    // Define una interfaz para el listener de datos recibidos
    public interface OnDataReceivedListener {
        void onDataReceived(List<Quote> datos);
    }
}