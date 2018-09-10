package com.isa.aem.tools;

import com.isa.aem.Currency;

import javax.faces.bean.ApplicationScoped;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@ApplicationScoped
public class SingleCurrency {

    private static final List<Currency> singleCurrency = new ArrayList<>();
    private static final List<Currency> singleCurrencyFirstChoice = new ArrayList<>();
    private static final List<Currency> singleCurrencySecondChoice= new ArrayList<>();

    public void add(Currency currency) {
        singleCurrency.add(currency);
    }

    public void addToFirstChoice(Currency currency) {
        singleCurrencyFirstChoice.add(currency);
    }

    public void addToSecondChoice(Currency currency) {
        singleCurrencySecondChoice.add(currency);
    }

    public List<Currency> getSingleCurrency() {
        return singleCurrency;
    }

    public static List<Currency> getSingleCurrencyFirstChoice() {
        return singleCurrencyFirstChoice;
    }

    public static List<Currency> getSingleCurrencySecondChoice() {
        return singleCurrencySecondChoice;
    }

    public void clear() {
        singleCurrency.clear();
    }


    public void sortSingleCurrencyByCourse() {
        Collections.sort(singleCurrency, (o1, o2) -> o1.getClose().compareTo(o2.getClose()));
    }

    public void sortSingleCurrencyByDate(List<Currency> list) {
        Collections.sort(list,(o1, o2) -> o1.getDate().compareTo(o2.getDate()));
    }
}