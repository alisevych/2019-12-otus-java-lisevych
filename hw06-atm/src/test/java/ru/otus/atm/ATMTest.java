package ru.otus.atm;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.security.sasl.AuthenticationException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.atm.Nominal.*;

class ATMTest {

    ATM atm;

    @BeforeAll
    public void setUp() {
        Map<Nominal, Integer> initialState = new HashMap<>();
        initialState.put(FIFTY, 10);
        initialState.put(HUNDRED, 20);
        // TWO_HUNDRED is not defined in cells
        initialState.put(FIVE_HUNDRED, 100);
        initialState.put(THOUSAND, 10);
        initialState.put(TWO_THOUSAND, 0);
        initialState.put(FIVE_THOUSAND, 1);
        Cells cells = new Cells(initialState);
        atm = new ATM(cells);
    }

    @Test
    void userAuthorize() {
        long key;
        try {
            key = atm.userAuthorize();
            System.out.println("Key = " + key);
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }
    }
}