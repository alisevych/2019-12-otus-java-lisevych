package ru.otus.department;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.otus.atm.AtmFactory;
import ru.otus.atm.AtmService;
import ru.otus.atm.Nominal;
import ru.otus.atm.ServiceSession;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static ru.otus.atm.Nominal.*;
import static ru.otus.atm.Nominal.FIVE_THOUSAND;

import static org.assertj.core.api.Assertions.*;

class AtmDepartmentTest {

    private static AtmDepartment lisDepartment = new AtmDepartmentImpl("Horns and hooves");

    @BeforeAll
    protected static void addSeveralAtms() {
        Map<Nominal, Integer> initialState1 = new TreeMap<>();
        initialState1.put(HUNDRED, 1);
        initialState1.put(TWO_HUNDRED, 2);
        initialState1.put(FIVE_HUNDRED, 5);
        initialState1.put(THOUSAND, 1);
        initialState1.put(TWO_THOUSAND, 0);
        initialState1.put(FIVE_THOUSAND, 10);
        AtmService atm1 = AtmFactory.generateAtm(initialState1, null);
        lisDepartment.addAtm(atm1);

        Map<Nominal, Integer> initialState2 = new TreeMap<>(initialState1);
        initialState2.put(HUNDRED, 15);
        AtmService atm2 = AtmFactory.generateAtm(initialState2, null);
        lisDepartment.addAtm(atm2);

        Map<Nominal, Integer> initialState3 = new TreeMap<>(initialState1);
        initialState2.put(HUNDRED, 0);
        AtmService atm3 = AtmFactory.generateAtm(initialState3, null);
        lisDepartment.addAtm(atm3);
    }

    @Test
    void getAtmIds() {
        Set<String> atmIDs = lisDepartment.getAtmIds();
        System.out.println("Department " + lisDepartment.getName());
        System.out.println("ATM IDs: " + atmIDs);
        assertThat(atmIDs.contains("atm1"));
        assertThat(atmIDs.contains("atm2"));
        assertThat(atmIDs.contains("atm3"));
        assertThat(!atmIDs.contains("atm4"));
        assertThat(!atmIDs.contains("atm0"));
    }

    @Test
    void atmIsAddedToDepartment() {
        String atmSpecialName = "atmSpecial";
        Map<Nominal, Integer> initialState1 = new TreeMap<>();
        initialState1.put(FIFTY, 100);
        initialState1.put(HUNDRED, 200);
        initialState1.put(FIVE_HUNDRED, 500);
        initialState1.put(THOUSAND, 1000);
        initialState1.put(TWO_THOUSAND, 10000);
        initialState1.put(FIVE_THOUSAND, 100000);
        AtmService atm1 = AtmFactory.generateAtm(initialState1, atmSpecialName);
        lisDepartment.addAtm(atm1);
        Set<String> atmIDs = lisDepartment.getAtmIds();
        System.out.println("Department " + lisDepartment.getName());
        System.out.println("ATM IDs: " + atmIDs);
        assertThat(atmIDs.contains(atmSpecialName));
    }

    @Test
    void atmFactorySavesGeneratedAmtIDs() {
        List<String> atmIDs = AtmFactory.getGeneratedIDs();
        System.out.println("Atm Factory. Generated IDs:" + atmIDs);
        assertThat(atmIDs.contains("atm1"));
        assertThat(atmIDs.contains("atm2"));
        assertThat(atmIDs.contains("atm3"));
        assertThat(!atmIDs.contains("atm4"));
        assertThat(!atmIDs.contains("atm0"));
    }

}