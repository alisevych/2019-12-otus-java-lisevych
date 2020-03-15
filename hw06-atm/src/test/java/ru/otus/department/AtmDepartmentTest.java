package ru.otus.department;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.otus.atm.*;
import ru.otus.money.Bundle;
import ru.otus.money.BundleImpl;
import ru.otus.money.Nominal;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static ru.otus.money.Nominal.*;

import static org.assertj.core.api.Assertions.*;

class AtmDepartmentTest {

    long serviceKey = 1234567890;
    private static AtmDepartment lisDepartment = new AtmDepartmentImpl("Horns and hooves");
    private static AtmService atm1;
    private static UserSession userSessionToAtm1;

    @BeforeAll
    protected static void addSeveralAtmsToDepartment() {
        Map<Nominal, Integer> initialMap1 = new TreeMap<>();
        initialMap1.put(HUNDRED, 1);
        initialMap1.put(TWO_HUNDRED, 2);
        initialMap1.put(FIVE_HUNDRED, 5);
        initialMap1.put(THOUSAND, 1);
        initialMap1.put(TWO_THOUSAND, 0);
        initialMap1.put(FIVE_THOUSAND, 10);
        State initialState1 = new StateImpl(initialMap1);
        atm1 = AtmBuilder.generateAtm(initialState1, null);
        lisDepartment.addAtm(atm1);

        initialMap1.put(HUNDRED, 15);
        State initialState2 = new StateImpl(initialMap1);
        AtmService atm2 = AtmBuilder.generateAtm(initialState2, null);
        lisDepartment.addAtm(atm2);

        initialMap1.put(HUNDRED, 0);
        State initialState3 = new StateImpl(initialMap1);
        AtmService atm3 = AtmBuilder.generateAtm(initialState3, null);
        lisDepartment.addAtm(atm3);

        insertCardEnterPinIntoAtm1();
    }

    private static void insertCardEnterPinIntoAtm1(){
        long card = 1234512345;
        userSessionToAtm1 = new UserSession((AtmUser) atm1, card);
        int pin = 1234;
        assertThat(userSessionToAtm1.enterPinCode(pin)).isTrue();
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
        Map<Nominal, Integer> initialMap1 = new TreeMap<>();
        initialMap1.put(FIFTY, 100);
        initialMap1.put(HUNDRED, 200);
        initialMap1.put(FIVE_HUNDRED, 500);
        initialMap1.put(THOUSAND, 1000);
        initialMap1.put(TWO_THOUSAND, 10000);
        initialMap1.put(FIVE_THOUSAND, 100000);
        AtmService atm1 = AtmBuilder.generateAtm(new StateImpl(initialMap1), atmSpecialName);
        lisDepartment.addAtm(atm1);
        Set<String> atmIDs = lisDepartment.getAtmIds();
        System.out.println("Department " + lisDepartment.getName());
        System.out.println("ATM IDs: " + atmIDs);
        assertThat(atmIDs.contains(atmSpecialName));
    }

    @Test
    void atmFactorySavesGeneratedAmtIDs() {
        List<String> atmIDs = AtmBuilder.getGeneratedIDs();
        System.out.println("Atm Factory. Generated IDs:" + atmIDs);
        assertThat(atmIDs.contains("atm1"));
        assertThat(atmIDs.contains("atm2"));
        assertThat(atmIDs.contains("atm3"));
        assertThat(!atmIDs.contains("atm4"));
        assertThat(!atmIDs.contains("atm0"));
    }

    @Test
    void getAtmBalance() {
        lisDepartment.requestAllAtmsBalance(serviceKey);
        lisDepartment.printReceivedAtmsBalance(serviceKey);
    }

    @Test
    void restoreAtmsInitialStates() {
        lisDepartment.requestAllAtmsBalance(serviceKey);
        lisDepartment.printReceivedAtmsBalance(serviceKey);
        System.out.println("-----------------------");
        Bundle banknotesPack = new BundleImpl();
        banknotesPack.addBanknotes(Nominal.THOUSAND, 1);
        banknotesPack.addBanknotes(Nominal.FIVE_HUNDRED, 150);
        banknotesPack.addBanknotes(Nominal.HUNDRED, 0);
        userSessionToAtm1.inputBanknotes(banknotesPack);
        userSessionToAtm1.getAmount(3000);
        lisDepartment.requestAllAtmsBalance(serviceKey);
        lisDepartment.printReceivedAtmsBalance(serviceKey);
        System.out.println("---ATM1 reinitialize---");
        lisDepartment.reinitializeAllAtms(serviceKey);
        lisDepartment.requestAllAtmsBalance(serviceKey);
        lisDepartment.printReceivedAtmsBalance(serviceKey);
        System.out.println("-----------------------");
        // second iteration
        banknotesPack = new BundleImpl();
        banknotesPack.addBanknotes(Nominal.THOUSAND, 1000);
        banknotesPack.addBanknotes(Nominal.FIVE_HUNDRED, 100);
        banknotesPack.addBanknotes(Nominal.HUNDRED, 0);
        userSessionToAtm1.inputBanknotes(banknotesPack);
        userSessionToAtm1.getAmount(30000);
        lisDepartment.requestAllAtmsBalance(serviceKey);
        lisDepartment.printReceivedAtmsBalance(serviceKey);
        System.out.println("---ATM1 reinitialize---");
        lisDepartment.reinitializeAllAtms(serviceKey);
        lisDepartment.requestAllAtmsBalance(serviceKey);
        lisDepartment.printReceivedAtmsBalance(serviceKey);
        System.out.println("-----------------------");
        // third iteration
        banknotesPack = new BundleImpl();
        banknotesPack.addBanknotes(Nominal.THOUSAND, 10);
        banknotesPack.addBanknotes(Nominal.FIVE_HUNDRED, 10);
        banknotesPack.addBanknotes(THOUSAND, 20); //summarizing same nominals in BundleImpl
        userSessionToAtm1.inputBanknotes(banknotesPack);
        lisDepartment.requestAllAtmsBalance(serviceKey);
        lisDepartment.printReceivedAtmsBalance(serviceKey);
        System.out.println("---ATM1 reinitialize---");
        lisDepartment.reinitializeAllAtms(serviceKey);
        lisDepartment.requestAllAtmsBalance(serviceKey);
        lisDepartment.printReceivedAtmsBalance(serviceKey);
    }
}