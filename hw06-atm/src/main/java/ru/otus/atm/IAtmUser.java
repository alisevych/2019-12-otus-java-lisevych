package ru.otus.atm;

import java.util.List;
import java.util.Map;

public interface IAtmUser {

    long userLogin();
    Map<Nominal, Integer> getAmount(int sum);
    void inputBanknotes(Map<Nominal, Integer> banknotes);
    List<Nominal> getAvailableNominals();
    void userLogout();

}
