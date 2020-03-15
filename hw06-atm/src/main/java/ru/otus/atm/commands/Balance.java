package ru.otus.atm.commands;

import ru.otus.atm.AtmService;

public class Balance implements Command {

    @Override
    public String execute(long key, AtmService atm) {
        atm.sendBalance();
        return "Balance sent from ATM " + atm.getAtmID();
    }
}
