package ru.otus.atm;

import java.util.Map;
import java.util.TreeMap;

import static ru.otus.atm.Nominal.*;

public class AtmFactory {

    private static final AuthorizationModule authorization =  new AuthorizationModule();
    private static Atm atm;

    public static IAtmUser getAtmForUser(Map<Nominal, Integer> initialState) {
        if (atm == null) {
            Cells cells = new Cells(initialState);
            atm = new Atm(cells, authorization);
        }
        return atm;
    }
}
