package ru.otus.atm;


public interface AtmService {

    String getAtmID();

    long serviceLogin(long serviceKey);

    void serviceLogout(long key);

    State getState(long key);

    boolean setState(long key, State cellsState);

    void sendBalance();

    boolean reinit(long key);

    boolean addBalanceListener(BalanceListener listener);

}
