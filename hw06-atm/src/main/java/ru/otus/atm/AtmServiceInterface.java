package ru.otus.atm;

import java.security.Key;
import java.util.Map;

public interface AtmServiceInterface {

    long serviceAuthorize();
    Map<Nominal, int> getState(long key);
    void getNominals(long key);
    void setState(long key, Map<Nominal, Integer> banknotes);
    void serviceLogout();

}
