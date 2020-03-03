package ru.otus.atm;

import java.util.Map;

public interface ICells {

    void addBanknotes( Nominal nominal, int quantity);
    Map<Nominal, Integer> getAmount(int sum);

}
