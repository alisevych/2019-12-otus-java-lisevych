package ru.otus.atm;

import java.util.Map;

public interface IAtmService {

    long serviceLogin(long serviceKey);

    Map<Nominal, Integer> getState(long key);

    void setState(long key, Map<Nominal, Integer> banknotes);

    void serviceLogout(long key);

}
