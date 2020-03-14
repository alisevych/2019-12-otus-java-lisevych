package ru.otus.atm;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.atm.Nominal.*;

class AtmUserAndServiceTest {

    private static AtmUser atm;
    private static final Map<Nominal, Integer> initialState = new TreeMap<>();
    private static UserSession userSession;
    private static ServiceSession serviceSession;

    @BeforeAll
    protected static void setUp() {
        // FIFTY is not defined in this cells
        initialState.put(HUNDRED, 1);
        initialState.put(TWO_HUNDRED, 2);
        initialState.put(FIVE_HUNDRED, 5);
        initialState.put(THOUSAND, 1);
        initialState.put(TWO_THOUSAND, 0);
        initialState.put(FIVE_THOUSAND, 10);
        atm = AtmFactory.generateAtm(initialState, null);
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
            Map<Nominal, Integer> banknotesGiven = userSession.getAmount(amount);
            assert false; // if exception is missed
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
    }

    @Test
    void inputBanknotesIntoAtm() {
        Map<Nominal, Integer> initialState = serviceSession.getState();
        Map<Nominal, Integer> banknotesPack = new TreeMap<>();
        banknotesPack.put(Nominal.THOUSAND, 1);
        banknotesPack.put(Nominal.FIVE_HUNDRED, 154);
        banknotesPack.put(Nominal.HUNDRED, 0);
        userSession.inputBanknotes(banknotesPack);
        Map<Nominal, Integer> expectedState = summarizeTwoMaps(initialState, banknotesPack);
        System.out.println("--- after input banknotes ---");
        Map<Nominal, Integer> resultState = serviceSession.getState();
        assertThat(serviceSession.setState(initialState));
        assertThat(resultState).isEqualTo(expectedState);
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

    @Test
    void getAndSetStateOfAtm() {
        Map<Nominal, Integer> initialState = serviceSession.getState();
        Map<Nominal, Integer> newState = new TreeMap<>();
        newState.put(FIFTY, 150);
        newState.put(TWO_HUNDRED, 200);
        newState.put(FIVE_HUNDRED, 500);
        newState.put(THOUSAND, 1000);
        newState.put(TWO_THOUSAND, 10);
        newState.put(FIVE_THOUSAND, 0);
        serviceSession.setState(newState);
        Map<Nominal, Integer> afterSetState = serviceSession.getState();
        assertThat(afterSetState.equals(newState));
        serviceSession.setState(initialState);
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