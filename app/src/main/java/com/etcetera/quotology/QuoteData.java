package com.etcetera.quotology;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class QuoteData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotedata);
    }
    private static QuoteData instance;
    private List<Quote> quotes;

    private QuoteData() {
        quotes = new ArrayList<>();
    }

    public static synchronized QuoteData getInstance() {
        if (instance == null) {
            instance = new QuoteData();
        }
        return instance;
    }

    public List<Quote> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<Quote> quotes) {
        this.quotes = quotes;
    }
}