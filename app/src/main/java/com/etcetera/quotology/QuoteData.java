package com.etcetera.quotology;

import java.util.ArrayList;
import java.util.List;

public class QuoteData {
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

    public void addQuote(Quote quote) {
        quotes.add(quote);
    }
}
