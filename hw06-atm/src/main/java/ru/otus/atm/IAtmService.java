package ru.otus.atm;

import java.util.Map;

public interface IAtmService {

    long serviceLogin();

    Map<Nominal, Integer> getState();

    void setState(Map<Nominal, Integer> banknotes);

    void serviceLogout();

}
