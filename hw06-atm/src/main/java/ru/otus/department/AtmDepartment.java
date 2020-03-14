package ru.otus.department;

import ru.otus.atm.AtmService;

import java.util.Set;


public interface AtmDepartment {

    Set<String> getAtmIds();

    boolean addAtm(AtmService newAtm);

    boolean getStateFromAllAtms();

    boolean restartAllAtms();

    String getName();

}
