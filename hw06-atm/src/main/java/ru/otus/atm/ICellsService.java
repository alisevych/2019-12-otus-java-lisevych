package ru.otus.atm;

import java.util.Map;

public interface ICellsService {

    void setState (Map<Nominal, Integer> initialState);
    Map<Nominal, Integer> getState ();

}
