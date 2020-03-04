package ru.otus.atm;

import java.util.List;
import java.util.Map;

interface ICells {

    void inputBanknotes(Map<Nominal, Integer> banknotes);

    Map<Nominal, Integer> takeAmountOut(int sum);

    List<Nominal> getAvailableNominals();

    void setState (Map<Nominal, Integer> initialState);

    Map<Nominal, Integer> getState ();

}
