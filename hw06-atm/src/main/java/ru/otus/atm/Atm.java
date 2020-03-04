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
    public long userLogin(long cardNumber, int pin){
        userSessionKey = authorization.authorizeToAtmAsUser(cardNumber, pin);
        return userSessionKey;
    }

    @Override
    @AuthorizedAs("user")
    public void userLogout() {
        userSessionKey = -1;
    }

    @Override
    public long serviceLogin(long serviceKey) {
        userSessionKey = -1; // terminate user session if any
        serviceSessionKey = authorization.authorizeToAtmAsService(serviceKey);
        return serviceSessionKey;
    }

    @AuthorizedAs("service")
    @Override
    public void serviceLogout(long key) {
        serviceSessionKey = -1;
    }

    @Override
    @AuthorizedAs("user")
    public Map<Nominal, Integer> getAmount(long key, int sum) {
        return cells.takeAmountOut(sum);
    }

    @Override
    @AuthorizedAs("user")
    public boolean inputBanknotes(long key, Map<Nominal, Integer> banknotes) {
        return cells.inputBanknotes(banknotes);
    }

    @Override
    @AuthorizedAs("user")
    public List<Nominal> getAvailableNominals(long key) {
        return cells.getAvailableNominals();
    }

    @Override
    @AuthorizedAs("service")
    public Map<Nominal, Integer> getState(long key) {
        return cells.getState();
    }

    @Override
    @AuthorizedAs("service")
    public boolean setState(long key, Map<Nominal, Integer> cellsState) {
        return cells.setState(cellsState);
    }

}
