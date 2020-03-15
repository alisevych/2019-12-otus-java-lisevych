package ru.otus.atm;

import ru.otus.atm.annotations.AuthorizedAs;
import ru.otus.atm.commands.*;
import ru.otus.money.Bundle;
import ru.otus.money.Nominal;

import java.util.List;

import static ru.otus.atm.annotations.AuthorizedAs.AsType.*;

public class AtmImpl implements AtmUser, AtmService, CommandListener {

    private final Cells cells;
    private final Authorization authorization;
    private final String atmID;
    private long userSessionKey=-1;
    private long serviceSessionKey=-1;
    private final State initialState;
    private final long internalKey = -111111111;
    private BalanceProducer balanceProducer = new BalanceProducer();

    AtmImpl(State initialState, Authorization authorization, String atmNumber) {
        this.cells = new CellsImpl(initialState);
        this.authorization = authorization;
        this.atmID = atmNumber;
        this.initialState = new StateImpl(initialState.getMap());
    }

    @Override
    public long userLogin(long cardNumber, int pin){
        userSessionKey = authorization.authorizeToAtmAsUser(cardNumber, pin);
        return userSessionKey;
    }

    @Override
    @AuthorizedAs(USER)
    public void userLogout() {
        userSessionKey = -1;
    }

    @Override
    public long serviceLogin(long serviceKey) {
        serviceSessionKey = authorization.authorizeToAtmAsService(serviceKey);
        return serviceSessionKey;
    }

    @Override
    @AuthorizedAs(SERVICE)
    public void serviceLogout(long key) {
        serviceSessionKey = -1;
    }

    @Override
    public String getAtmID(){
        return atmID;
    }

    @Override
    @AuthorizedAs(USER)
    public Bundle getAmount(long key, int sum) {
        return cells.takeAmountOut(sum);
    }

    @Override
    @AuthorizedAs(USER)
    public boolean inputBanknotes(long key, Bundle banknotes) {
        return cells.inputBanknotes(banknotes);
    }

    @Override
    @AuthorizedAs(USER)
    public List<Nominal> getAvailableNominals(long key) {
        return cells.getAvailableNominals();
    }

    @Override
    @AuthorizedAs(SERVICE)
    public State getState(long key) {
        return cells.getState();
    }

    @Override
    @AuthorizedAs(SERVICE)
    public boolean setState(long key, State cellsState) {
        return cells.setState(cellsState);
    }

    public boolean reinit(long key) {
        if (initialState == null) {
            throw new IllegalStateException("No initial state is stored in atm " + atmID);
        }
        return setState(key, initialState);
    }

    @Override
    public boolean addBalanceListener(BalanceListener listener) {
        balanceProducer.addListener(listener);
        return true;
    }

    public void sendBalance() {
        long balance = cells.getState().calculateBalance();
        balanceProducer.sendBalance(atmID, balance);
    }

    @Override
    public void onCommand(long key, Command command) {
        command.execute(key, this);
    }
}
