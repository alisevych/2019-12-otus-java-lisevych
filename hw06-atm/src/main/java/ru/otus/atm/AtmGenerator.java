package ru.otus.atm;

import java.util.Map;

public class AtmGenerator {

    private static final AuthorizationModule authorization =  new AuthorizationModule();
    private static AtmImpl atm;

    public static AtmUser getAtmForUser(Map<Nominal, Integer> initialState) {
        if (atm == null) {
            CellsImpl cells = new CellsImpl(initialState);
            atm = new AtmImpl(cells, authorization);
        }
        return atm;
    }

    public static AtmService getAtmForService(Map<Nominal, Integer> initialState) {
        return (AtmService) getAtmForUser(initialState);
    }
}
