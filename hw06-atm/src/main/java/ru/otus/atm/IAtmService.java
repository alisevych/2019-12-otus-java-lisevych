package ru.otus.atm;

import java.util.Map;

public interface IAtmService {

    long serviceLogin(long serviceKey);

    Map<Nominal, Integer> getState(long key);

    boolean setState(long key, Map<Nominal, Integer> cellsState);

    void serviceLogout(long key);

}
