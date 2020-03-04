package ru.otus.atm;

import java.util.List;
import java.util.Map;

public interface IAtmUser {

    long userLogin();

    Map<Nominal, Integer> getAmount(long key, int sum);

    void inputBanknotes(long key, Map<Nominal, Integer> banknotes);

    List<Nominal> getAvailableNominals(long key);

    void userLogout(long key);

}
