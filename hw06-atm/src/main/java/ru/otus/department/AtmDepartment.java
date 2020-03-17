package ru.otus.department;

import ru.otus.atm.AtmService;

import java.util.Set;


public interface AtmDepartment {

    Set<String> getAtmIds();

    boolean addAtm(AtmService newAtm);

    boolean requestAllAtmsBalance(long serviceKey);

    boolean printReceivedAtmsBalance(long serviceKey);

    boolean reinitializeAllAtms(long serviceKey);

    String getName();

}
