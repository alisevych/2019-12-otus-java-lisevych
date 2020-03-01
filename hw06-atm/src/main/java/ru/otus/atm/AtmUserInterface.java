package ru.otus.atm;

import javax.security.sasl.AuthenticationException;
import java.util.Map;

public interface AtmUserInterface {

    long userAuthorize() throws AuthenticationException;
    void getAmount(long key, int sum) throws AuthenticationException;
    void inputBanknotes(long key, Map<Nominal, Integer> banknotes) throws AuthenticationException;
    void getAvailableNominals(long key) throws AuthenticationException;
    void userLogout();

}
