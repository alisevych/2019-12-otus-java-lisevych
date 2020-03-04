package ru.otus.atm;

import ru.otus.atm.annotations.AuthorizedAs;

import java.util.List;
import java.util.Map;

public class Atm implements IAtmUser, IAtmService {

    private ICells cells;
    private IAuthorization authorization;
    private long userSessionKey=-1;
    private long serviceSessionKey=-1;

    Atm(ICells cells, IAuthorization authorization) {
        this.cells = cells;
        this.authorization = authorization;
    }

    @Override
    public long userLogin(){
        userSessionKey = authorization.authorizeToAtmAsUser();
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
        serviceSessionKey = authorization.authorizeToAtmAsService();
        return serviceSessionKey;
    }

    @Override
    public void serviceLogout() {
        serviceSessionKey = -1;
    }

    @Override
    @AuthorizedAs("user")
    public Map<Nominal, Integer> getAmount(int sum) {
        return cells.takeAmountOut(sum);
    }

    @Override
    @AuthorizedAs("user")
    public void inputBanknotes(Map<Nominal, Integer> banknotes) {
        cells.inputBanknotes(banknotes);
    }

    @Override
    @AuthorizedAs("user")
    public List<Nominal> getAvailableNominals() {
        return cells.getAvailableNominals();
    }

    @Override
    @AuthorizedAs("service")
    public Map<Nominal, Integer> getState() {
        return cells.getState();
    }

    @Override
    @AuthorizedAs("service")
    public void setState(Map<Nominal, Integer> cellsState) {
        cells.setState(cellsState);
    }

}
