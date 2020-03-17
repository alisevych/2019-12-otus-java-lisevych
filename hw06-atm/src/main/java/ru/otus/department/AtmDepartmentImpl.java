package ru.otus.department;

import ru.otus.atm.AtmService;
import ru.otus.atm.BalanceListener;
import ru.otus.atm.commands.CommandListener;
import ru.otus.atm.commands.Balance;
import ru.otus.atm.commands.Reinit;

import java.util.*;

public class AtmDepartmentImpl implements AtmDepartment {

    private final String name;
    private Map<String, AtmService> atms = new TreeMap<>();
    private CommandProducer commandProducer = new CommandProducer();
    private BalanceListener balanceListener = new BalanceListener();

    AtmDepartmentImpl(String name) {
        this.name = name;
    }

    @Override
    public Set<String> getAtmIds() {
        return new TreeSet<>(atms.keySet());
    }

    @Override
    public boolean addAtm(AtmService newAtm) {
        String newAtmId = newAtm.getAtmID();
        if (atms.containsKey(newAtmId)){
            throw new RuntimeException("[ERROR] atm with this ID " + newAtmId +
                    " already exists in department " + this.name);
        }
        atms.put(newAtmId , newAtm);
        commandProducer.addListener((CommandListener) newAtm);
        newAtm.addBalanceListener(this.balanceListener);
        return false;
    }

    @Override
    public boolean requestAllAtmsBalance(long serviceKey) {
        commandProducer.send(serviceKey, new Balance());
        return true;
    }

    @Override
    public boolean printReceivedAtmsBalance(long serviceKey) {
        balanceListener.printAll();
        return true;
    }

    @Override
    public boolean reinitializeAllAtms(long serviceKey) {
        commandProducer.send(serviceKey, new Reinit());
        return true;
    }

    @Override
    public String getName() {
        return name;
    }

}
