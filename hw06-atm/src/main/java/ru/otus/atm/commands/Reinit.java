package ru.otus.atm.commands;

import ru.otus.atm.AtmService;

public class Reinit implements Command {

    @Override
    public String execute(long key, AtmService atm) {
        atm.reinit(key);
        return "Balance sent from ATM " + atm.getAtmID();
    }
}
