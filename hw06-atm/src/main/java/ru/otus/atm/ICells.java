package ru.otus.atm;

import java.util.Map;

public interface ICells {

    void inputBanknotes(Map<Nominal, Integer> banknotes);
    Map<Nominal, Integer> getAmount(int sum);

}
