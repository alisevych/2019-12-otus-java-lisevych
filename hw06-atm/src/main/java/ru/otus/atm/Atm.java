package ru.otus.atm;

import java.util.List;
import java.util.Map;

public class Atm implements IAtmUser, IAtmService {

    private Cells cells;
    private AuthorizationModule authorization;
    private long userSessionKey=-1;
    private long serviceSessionKey=-1;

    Atm(Cells cells, AuthorizationModule authorization) {
        this.cells = cells;
        this.authorization = authorization;
    }

    @Override
    public long userLogin(){
        userSessionKey = authorization.authorizeAsUser();
        return userSessionKey;
    }

    @Override
    public void userLogout() {
        userSessionKey = -1;
    }

    @Override
    public long serviceLogin() {
        if (userSessionKey != -1) {
            userLogout();
        }
        serviceSessionKey = authorization.authorizeAsService();
        return serviceSessionKey;
    }

    @Override
    public void serviceLogout() {
        serviceSessionKey = -1;
    }

    @Override
    public Map<Nominal, Integer> getAmount(int sum) {
        //ToDo authorization check
        return cells.getAmount(sum);
    }

    @Override
    public void inputBanknotes(Map<Nominal, Integer> banknotes) {
        //ToDo authorization check
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Nominal> getAvailableNominals() {
        //ToDo authorization check
        return cells.getAvailableNominals();
    }

    @Override
    public Map<Nominal, Integer> getState() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setState(Map<Nominal, Integer> banknotes) {
        throw new UnsupportedOperationException();
    }

}
