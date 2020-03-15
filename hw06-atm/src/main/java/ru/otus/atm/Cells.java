package ru.otus.atm;

import ru.otus.money.Bundle;
import ru.otus.money.Nominal;

import java.util.List;

interface Cells {

    boolean inputBanknotes(Bundle banknotes);

    Bundle takeAmountOut(int sum);

    List<Nominal> getAvailableNominals();

    boolean setState (State cellsState);

    State getState ();

}
