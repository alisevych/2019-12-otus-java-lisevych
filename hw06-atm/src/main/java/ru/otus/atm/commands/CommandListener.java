package ru.otus.atm.commands;

public interface CommandListener {

    void onCommand(long key, Command command);

}
