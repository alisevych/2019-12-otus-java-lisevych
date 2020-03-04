package ru.otus.atm;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.atm.Nominal.*;

class AtmUserTest {

    private static IAtmUser atm;
    private static Map<Nominal, Integer> initialState = new TreeMap<>();
    private static UserSession userSession;

    private long serviceKey = 1235467890;

    @BeforeAll
    private static void setUp() {
        // FIFTY is not defined in this cells
        initialState.put(HUNDRED, 1);
        initialState.put(TWO_HUNDRED, 2);
        initialState.put(FIVE_HUNDRED, 5);
        initialState.put(THOUSAND, 1);
        initialState.put(TWO_THOUSAND, 0);
        initialState.put(FIVE_THOUSAND, 10);
        atm = AtmFactory.getAtmForUser(initialState);
        insertCardEnterPin();
    }

    private static void insertCardEnterPin(){
        long card = 1234512345;
        userSession = new UserSession(atm, card);
        int pin = 1234;
        assertThat(userSession.enterPinCode(pin)).isTrue();
    }

    private static void openServiceSessionAndGetAtmState(){
        //ToDo ServiceSession
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
            Map<Nominal, Integer> banknotesGiven = userSession.getAmount(amount);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertThat(e.getMessage()).contains("Could not get");
        }
    }

    @Test
    void getAvailableAmountFromAtm() {
        int amount = 3100;
        Map<Nominal, Integer> banknotesGiven = userSession.getAmount(amount);
        System.out.println("User requested amount from Atm: " + amount);
        Map<Nominal, Integer> expected = new TreeMap<>();
        expected.put(Nominal.THOUSAND, 1);
        expected.put(Nominal.FIVE_HUNDRED, 4);
        expected.put(Nominal.HUNDRED, 1);
        assertThat(banknotesGiven).isEqualTo(expected);
        outputBanknotesMap(banknotesGiven);
    }

    @Test
    void inputBanknotesIntoAtm() {
        Map<Nominal, Integer> initialState = ((IAtmService) atm).getState(serviceKey);
        Map<Nominal, Integer> banknotesPack = new TreeMap<>();
        banknotesPack.put(Nominal.THOUSAND, 1);
        banknotesPack.put(Nominal.FIVE_HUNDRED, 154);
        banknotesPack.put(Nominal.HUNDRED, 0);
        userSession.inputBanknotes(banknotesPack);
        Map<Nominal, Integer> expectedState = summarizeTwoMaps(initialState, banknotesPack);
        System.out.println("--- after input banknotes ---");
        Map<Nominal, Integer> resultState = ((IAtmService) atm).getState(serviceKey);
        assertThat(resultState).isEqualTo(expectedState);
        ((IAtmService) atm).setState(serviceKey, initialState); //return initial state
    }

    @Test
    void inputUnsupportedBanknotesIntoAtm() {
        try {
            Map<Nominal, Integer> banknotesPack = new TreeMap<>();
            banknotesPack.put(Nominal.THOUSAND, 1);
            banknotesPack.put(Nominal.FIVE_HUNDRED, 4);
            banknotesPack.put(Nominal.FIFTY, 15);
            userSession.inputBanknotes(banknotesPack);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertThat(e.getMessage()).contains("50 is not supported");
        }
    }

    private void outputBanknotesMap(Map<Nominal, Integer> banknotes) {
        banknotes.forEach((n,v) -> System.out.println(n + " - "+ v));
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