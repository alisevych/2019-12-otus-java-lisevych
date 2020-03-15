package ru.otus.atm.commands;

import ru.otus.atm.AtmService;

public interface Command {

    String execute(long key, AtmService atm);

}
