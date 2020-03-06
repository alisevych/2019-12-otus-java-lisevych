package ru.otus.atm;

import java.util.List;
import java.util.Map;

interface Cells {

    boolean inputBanknotes(Map<Nominal, Integer> banknotes);

    Map<Nominal, Integer> takeAmountOut(int sum);

    List<Nominal> getAvailableNominals();

    boolean setState (Map<Nominal, Integer> cellsState);

    Map<Nominal, Integer> getState ();

}
