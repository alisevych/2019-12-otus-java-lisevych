package ru.otus.atm;

import ru.otus.money.Bundle;
import ru.otus.money.Nominal;

import java.util.List;

public interface AtmUser {

    String getAtmID();

    long userLogin(long cardNumber, int pin);

    Bundle getAmount(long key, int sum);

    boolean inputBanknotes(long key, Bundle banknotes);

    List<Nominal> getAvailableNominals(long key);

    void userLogout();

}
