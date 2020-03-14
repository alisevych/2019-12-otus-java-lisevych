package ru.otus.atm;

import java.util.Map;

public interface AtmService {

    String getAtmID();

    long serviceLogin(long serviceKey);

    Map<Nominal, Integer> getState(long key);

    boolean setState(long key, Map<Nominal, Integer> cellsState);

    void serviceLogout(long key);

}
