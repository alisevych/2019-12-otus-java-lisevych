package ru.otus.atm;

import ru.otus.money.Nominal;

import java.util.Map;

public interface State {

    Map<Nominal, Integer> getMap();

    long calculateBalance();

    void print();

}
