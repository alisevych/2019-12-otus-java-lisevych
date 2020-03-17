package ru.otus.atm;

import ru.otus.money.Nominal;

import java.util.Map;

public interface State {

    Map<Nominal, Integer> getImmutableMap();

    Map<Nominal, Integer> getMapCopy();

    long calculateBalance();

    void print();

}
