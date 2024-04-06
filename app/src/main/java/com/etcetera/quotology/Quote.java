package com.etcetera.quotology;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Quote extends AppCompatActivity {
    private String quote;
    private String author;

    public Quote(String quote, String author) {
        this.quote = quote;
        this.author = author;
    }
    // Método getter para quote
    public String getQuote() {
        return quote;
    }

    // Método getter para author
    public String getAuthor() {
        return author;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);
    }
}