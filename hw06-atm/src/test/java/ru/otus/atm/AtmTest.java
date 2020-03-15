package ru.otus.atm;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.otus.money.Bundle;
import ru.otus.money.BundleImpl;
import ru.otus.money.Nominal;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.money.Nominal.*;

class AtmTest {

    private static AtmUser atm;
    private static final Map<Nominal, Integer> initialCellsMap = new TreeMap<>();
    private static UserSession userSession;
    private static ServiceSession serviceSession;

    @BeforeAll
    protected static void setUp() {
        // FIFTY is not defined in this cells
        initialCellsMap.put(HUNDRED, 1);
        initialCellsMap.put(TWO_HUNDRED, 2);
        initialCellsMap.put(FIVE_HUNDRED, 5);
        initialCellsMap.put(THOUSAND, 1);
        initialCellsMap.put(TWO_THOUSAND, 0);
        initialCellsMap.put(FIVE_THOUSAND, 10);
        atm = AtmBuilder.generateAtm(new StateImpl(initialCellsMap), null);
        insertCardEnterPin();
        openServiceSession();
    }

    private static void insertCardEnterPin(){
        long card = 1234512345;
        userSession = new UserSession(atm, card);
        int pin = 1234;
        assertThat(userSession.enterPinCode(pin)).isTrue();
    }

    private static void openServiceSession() {
        long serviceKey = 1234567890;
        serviceSession = new ServiceSession((AtmService) atm, serviceKey);
    }

    @Test
    void userGetAvailableNominals() {
        List<Nominal> nominals = userSession.getAvailableNominals();
        nominals.forEach(System.out::println);
        assertThat(nominals.size()).isGreaterThan(0);
    }

    @Test
    void canNotGetAmountFromAtm() {
        int amount = 4900;
        try{
            System.out.println("User requested amount from Atm: " + amount);
            Bundle banknotesGiven = userSession.getAmount(amount);
            //assert false; // if exception is missed
        } catch (Exception e) {
            System.out.println(e.getMessage());
            //assertThat(e.getMessage()).contains("Could not get");
            serviceSession.getState().print();
        }
    }

    @Test
    void getAvailableAmountFromAtm() {
        ((AtmService) atm).reinit(-111111111);
        int amount = 3100;
        Bundle banknotesGiven = userSession.getAmount(amount);
        System.out.println("User requested amount from Atm: " + amount);
        Bundle expected = new BundleImpl();
        expected.addBanknotes(Nominal.THOUSAND, 1);
        expected.addBanknotes(Nominal.FIVE_HUNDRED, 4);
        expected.addBanknotes(Nominal.HUNDRED, 1);
        assertThat(banknotesGiven).isEqualTo(expected);
    }

    @Test
    void inputBanknotesIntoAtm() {
        State initialState = serviceSession.getState();
        initialState.print();
        System.out.println("----------------");
        Bundle banknotesPack = new BundleImpl();
        banknotesPack.addBanknotes(Nominal.THOUSAND, 1);
        banknotesPack.addBanknotes(Nominal.FIVE_HUNDRED, 154);
        banknotesPack.addBanknotes(Nominal.HUNDRED, 0);
        userSession.inputBanknotes(banknotesPack);
        Map<Nominal, Integer> expectedMap = summarizeTwoMaps(initialState.getMap(), banknotesPack.getMap());
        System.out.println("--- after input banknotes ---");
        State resultState = serviceSession.getState();
        resultState.print();
        //assertThat(serviceSession.setState(initialState));
        assertThat(resultState.getMap()).isEqualTo(expectedMap);
    }

    @Test
    void inputUnsupportedBanknotesIntoAtm() {
        try {
            Bundle banknotesPack = new BundleImpl();
            banknotesPack.addBanknotes(Nominal.THOUSAND, 1);
            banknotesPack.addBanknotes(Nominal.FIVE_HUNDRED, 4);
            banknotesPack.addBanknotes(Nominal.FIFTY, 15);
            userSession.inputBanknotes(banknotesPack);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertThat(e.getMessage()).contains("50 is not supported");
        }
    }

    @Test
    void getAndSetStateOfAtm() {
        State initialState = serviceSession.getState();
        initialState.print();
        System.out.println("----------------");
        Map<Nominal, Integer> newStateMap = new TreeMap<>();
        newStateMap.put(FIFTY, 150);
        newStateMap.put(TWO_HUNDRED, 200);
        newStateMap.put(FIVE_HUNDRED, 500);
        newStateMap.put(THOUSAND, 1000);
        newStateMap.put(TWO_THOUSAND, 10);
        newStateMap.put(FIVE_THOUSAND, 0);
        State newState = new StateImpl(newStateMap);
        serviceSession.setState(newState);
        State afterSetState = serviceSession.getState();
        afterSetState.print();
        assertThat(afterSetState.equals(newState));
        //serviceSession.setState(initialState);
    }

    @Test
    void restoreInitialStateOfAtm() {
        State initialState = serviceSession.getState();
        Map<Nominal, Integer> newStateMap = new TreeMap<>();
        newStateMap.put(FIFTY, 150);
        newStateMap.put(TWO_HUNDRED, 200);
        newStateMap.put(FIVE_HUNDRED, 500);
        newStateMap.put(THOUSAND, 1000);
        newStateMap.put(TWO_THOUSAND, 10);
        newStateMap.put(FIVE_THOUSAND, 0);
        State newState = new StateImpl(newStateMap);
        serviceSession.setState(newState);
        userSession.getAmount(1500);
        userSession.inputBanknotes(new BundleImpl().addBanknotes(THOUSAND, 10));
        serviceSession.getState().print();
        ((AtmService) atm).reinit(-111111111);
        serviceSession.getState().print();
    }


    private Map<Nominal, Integer> summarizeTwoMaps(Map<Nominal, Integer> initialMap, Map<Nominal, Integer> addedBanknotes) {
        if (!initialMap.keySet().containsAll(addedBanknotes.keySet())){
            throw new RuntimeException("[ERROR] Some keys from addedBanknotes are not present in initialMap");
        }
        Map<Nominal, Integer> resultMap = new TreeMap<>(initialMap);
        for (Nominal nominal : initialMap.keySet()){
            if (addedBanknotes.containsKey(nominal)) {
                resultMap.put(nominal, initialMap.get(nominal) + addedBanknotes.get(nominal));
            }
        }
        return resultMap;
    }
}