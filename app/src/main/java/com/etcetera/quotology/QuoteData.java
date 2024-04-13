package com.etcetera.quotology;

import java.util.ArrayList;
import java.util.List;

public class QuoteData {
    private static QuoteData instance;
    private List<Quote> entregable4;

    private QuoteData() {
        entregable4 = new ArrayList<>();
    }

    public static synchronized QuoteData getInstance() {
        if (instance == null) {
            instance = new QuoteData();
        }
        return instance;
    }

    public List<Quote> getentregable4() {
        return entregable4;
    }

    public void addQuote(Quote quote) {
        entregable4.add(quote);
    }
}
