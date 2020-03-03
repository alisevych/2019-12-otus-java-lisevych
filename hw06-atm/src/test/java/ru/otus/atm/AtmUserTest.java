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

    @BeforeAll
    private static void setUp() {
        Map<Nominal, Integer> initialState = new TreeMap<>();
        // FIFTY is not defined in this cells
        initialState.put(HUNDRED, 1);
        initialState.put(TWO_HUNDRED, 2);
        initialState.put(FIVE_HUNDRED, 5);
        initialState.put(THOUSAND, 1);
        initialState.put(TWO_THOUSAND, 0);
        initialState.put(FIVE_THOUSAND, 10);
        atm = AtmFactory.getAtmForUser(initialState);
    }

    @Test
    void userAuthorize() {
        long key;
        key = atm.userLogin();
        System.out.println("Key = " + key);
        assertThat(key).isGreaterThan(0);
    }

    @Test
    void userGetAvailableNominals() {
        List<Nominal> nominals = atm.getAvailableNominals();
        nominals.forEach(System.out::println);
        assertThat(nominals.size()).isGreaterThan(0);
    }

    @Test
    void getInvalidAmountFromAtm() {
        try{
            Map<Nominal, Integer> banknotesGiven = atm.getAmount(4900);
            assertThat(banknotesGiven).isNull();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertThat(e.getMessage()).contains("Could not get");
        }
    }

    @Test
    void getAmountFromAtm() {
        try{
            Map<Nominal, Integer> banknotesGiven = atm.getAmount(4900);
            assertThat(banknotesGiven).isNull();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertThat(e.getMessage()).contains("Could not get");
        }
    }

}