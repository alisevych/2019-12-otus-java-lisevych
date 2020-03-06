package ru.otus.atm;

import java.util.List;
import java.util.Map;

public interface IAtmUser {

    long userLogin(long cardNumber, int pin);

    Map<Nominal, Integer> getAmount(long key, int sum);

    boolean inputBanknotes(long key, Map<Nominal, Integer> banknotes);

    List<Nominal> getAvailableNominals(long key);

    void userLogout();

}
