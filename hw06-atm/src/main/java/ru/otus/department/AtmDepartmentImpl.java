package ru.otus.department;

import ru.otus.atm.AtmService;

import java.util.*;

public class AtmDepartmentImpl implements AtmDepartment {

    private final String name;
    private Map<String, AtmService> atms = new TreeMap<>();

    AtmDepartmentImpl(String name) {
        this.name = name;
    }

    @Override
    public Set<String> getAtmIds() {
        return new HashSet<>(atms.keySet());
    }

    @Override
    public boolean addAtm(AtmService newAtm) {
        String newAtmId = newAtm.getAtmID();
        if (atms.containsKey(newAtmId)){
            throw new RuntimeException("[ERROR] atm with this ID " + newAtmId +
                    " already exists in department " + this.name);
        }
        atms.put(newAtmId , newAtm);
        return false;
    }

    @Override
    public boolean getStateFromAllAtms() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean restartAllAtms() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getName() {
        return name;
    }
}
